package com.example.todolist.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.entity.SortType
import com.example.todolist.doamin.UseCase.ToggleCompletedUseCase
import com.example.todolist.doamin.UseCase.ToggleFavoriteUseCase
import com.example.todolist.repository.TodoRepo
import com.example.todolist.ui.home.components.AppMenuItem
import com.example.todolist.ui.home.delegae.TodoDelegate
import com.example.todolist.ui.home.event.HomeEvent
import com.example.todolist.ui.home.state.TabUiState
import com.example.todolist.ui.home.state.TodoGroupUiState
import com.example.todolist.ui.home.state.TodoPageUiState
import com.example.todolist.ui.home.state.TodoUiState
import com.example.todolist.ui.home.state.toTabUiState
import com.example.todolist.ui.home.state.toTodoUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


const val ID_ADD_NEW_LIST = -999L
const val ID_FAVORITE_LIST = -1000L
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: TodoRepo,
    private val toggleCompletedUseCase: ToggleCompletedUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel(), TodoDelegate {


    private val _eventFlow : MutableSharedFlow<HomeEvent> = MutableSharedFlow()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _listTodoGroup : MutableStateFlow<List<TodoGroupUiState>> = MutableStateFlow(emptyList())
    val listTodoGroup = _listTodoGroup.asStateFlow()

    val listTabGroup = listTodoGroup.map {
        val favoriteGroup = TodoGroupUiState(
            tab = TabUiState(
                id = ID_FAVORITE_LIST,
                title = "⭐",
                sortType = SortType.FAVORITE
            ),
            page = TodoPageUiState(
                activeTodoList = it
                    .flatMap { tab -> tab.page.activeTodoList }
                    .filter { todo -> todo.isFavorite }
                    .sortedByDescending { todo -> todo.createdAt },
                completedTodoList = emptyList()
            )
        )
        fun List<TodoUiState>.sortByType(sortType: SortType): List<TodoUiState> {
            return when (sortType) {
                SortType.FAVORITE -> sortedByDescending { it.isFavorite }
                else -> sortedByDescending { it.createdAt }
            }
        }

        val sortedTabs = it.map { tabItem ->
            tabItem.copy(
                page = tabItem.page.copy(
                    activeTodoList = tabItem.page.activeTodoList.sortByType(tabItem.tab.sortType),
                    completedTodoList = tabItem.page.completedTodoList
                        .sortedByDescending { it.updatedAt }
                )
            )
        }
        val addNewTab = TodoGroupUiState(
            tab = TabUiState(ID_ADD_NEW_LIST, "Add New List", SortType.CREATED_DATE),
            page = TodoPageUiState(emptyList(), emptyList())
        )

        listOf(favoriteGroup) + sortedTabs + addNewTab

    }


    private var _currentSelectedCollectionId: Long = -1L

    init {
        viewModelScope.launch {
            val listTodoCollections = repo.getTodoCollection()
            val listTabGroupUiState = listTodoCollections.ifEmpty {
                repo.addTodoCollection("My Tasks")?.let { collection ->
                    val id = collection.id
                    repo.addTodo("Task 1", id)
                    repo.addTodo("Task 2", id)
                    repo.addTodo("Task 3", id)

                }
                repo.getTodoCollection()
            }.map { taskCollection ->
                val collectionId = taskCollection.id
                val listTaskUiState = repo.getTodos(collectionId).map { taskEntity ->
                    taskEntity.toTodoUIState()
                }
                val tabUiState = taskCollection.toTabUiState()
                TodoGroupUiState(
                    tabUiState, TodoPageUiState(
                        activeTodoList = listTaskUiState.filter { !it.isCompleted },
                        completedTodoList = listTaskUiState.filter { it.isCompleted }
                    ))
            }
            _listTodoGroup.value = listTabGroupUiState
        }
    }

    override fun invertTodoFavorite(todoUiState: TodoUiState) {

        viewModelScope.launch {
            val updated = toggleFavoriteUseCase(todoUiState) ?: return@launch
            _listTodoGroup.value  = TaskReducer.updateTask(_listTodoGroup.value, updated)

        }
    }

    override fun invertTodoCompleted(todoUiState: TodoUiState) {
        viewModelScope.launch {
            val updated = toggleCompletedUseCase(todoUiState) ?: return@launch
            _listTodoGroup.value = TaskReducer.updateTask(_listTodoGroup.value, updated)

        }
    }

    override fun addCloseable(closeable: AutoCloseable) {
            super.addCloseable(closeable)
    }

    override fun addNewCollection(title: String) {
        viewModelScope.launch {
            repo.addTodoCollection(title)?.let { taskCollection ->
                val tabUiState = taskCollection.toTabUiState()
                val newTabGroup = TodoGroupUiState(tabUiState, TodoPageUiState(emptyList(), emptyList()))
                _listTodoGroup.value += newTabGroup
            }
        }
    }

    override fun addNewTodo(collectionId: Long, content: String) {
        viewModelScope.launch {
            repo.addTodo(content, collectionId)?.let { taskEntity ->
                val newTaskUiState = taskEntity.toTodoUIState()
                _listTodoGroup.value.let { listTabGroup ->
                    val newTabGroup = listTabGroup.map { tabGroup ->
                        if (tabGroup.tab.id == collectionId) {
                            val newPage = tabGroup.page.copy(
                                activeTodoList = (tabGroup.page.activeTodoList + newTaskUiState)
                            )
                            tabGroup.copy(page = newPage)
                        } else {
                            tabGroup
                        }
                    }
                    _listTodoGroup.value = newTabGroup
                }
            }
        }
    }

    override fun addNewTodoToCurrentCollection(content: String) {
        viewModelScope.launch {
            _listTodoGroup.value.firstOrNull { it.tab.id == _currentSelectedCollectionId }?.let { currentTab ->
                val collectionId = currentTab.tab.id
                collectionId?.let {
                    if (it > 0) {
                        addNewTodo(collectionId, content)
                    }
                }
            }
        }
    }

    override fun currentCollectionId(): Long {
        return _currentSelectedCollectionId
    }

    override fun requestAddNewCollection() {
        viewModelScope.launch {
            _eventFlow.emit(HomeEvent.RequestAddNewCollection)
        }
    }

    override fun requestSortTodo(collectionId: Long?) {
        viewModelScope.launch {
            _eventFlow.emit(
                HomeEvent.RequestShowBottomSheetOptions(
                    listOf(
                        AppMenuItem("Sort By Favorite") {
                            sortCollectionBy(collectionId, SortType.FAVORITE)
                        },
                        AppMenuItem("Sort By Created Date") {
                            sortCollectionBy(collectionId, SortType.CREATED_DATE)
                        }
                    )))


        }
    }

    override fun requestUpdateCollection(collectionId: Long) {
        Log.d("MainViewModel", "Request update collection $collectionId")
        // Delete Collection
        // Rename Collection
        val actionList = listOf(
            AppMenuItem("Delete Collection") {
                Log.d("MainViewModel", "Request Delete Collection $collectionId")
                deleteCollectionById(collectionId)
            },
            AppMenuItem("Rename Collection") {
                Log.d("MainViewModel", "Request Rename Collection $collectionId")
            }
        )
        viewModelScope.launch {
            _eventFlow.emit(HomeEvent.RequestShowBottomSheetOptions(actionList))
        }
    }
    private fun deleteCollectionById(collectionId: Long) {
        viewModelScope.launch {
            if (repo.deleteTodoCollectionById(collectionId)) {
                _listTodoGroup.value.let { listTabs ->
                    val newTabGroup = listTabs.filter { tabItem -> tabItem.tab.id != collectionId }
                    _listTodoGroup.value = newTabGroup
                }
            }
        }
    }

    private fun sortCollectionBy(collectionId: Long?, sortType: SortType) {
        viewModelScope.launch {
            if (repo.updateCollectionSortType(collectionId, sortType)) {
                _listTodoGroup.value.let { listTabs ->
                    val newTabGroup = listTabs.map { tabGroup ->
                        if (tabGroup.tab.id == collectionId) {
                            tabGroup.copy(tab = tabGroup.tab.copy(sortType = sortType))
                        } else {
                            tabGroup
                        }
                    }
                    _listTodoGroup.value = newTabGroup
                }
            }
        }
    }

    override fun updateCurrentCollectionId(collectionId: Long) {
        _currentSelectedCollectionId = collectionId
    }
}
object TaskReducer {

    fun updateTask(
        list: List<TodoGroupUiState>,
        updatedTodo: TodoUiState
    ): List<TodoGroupUiState> {
        return list.map { tabGroup ->
            val allTasks =
                tabGroup.page.activeTodoList + tabGroup.page.completedTodoList

            val newList = allTasks.map {
                if (it.id == updatedTodo.id) updatedTodo else it
            }

            val newPage = tabGroup.page.copy(
                activeTodoList = newList.filter { !it.isCompleted },
                completedTodoList = newList.filter { it.isCompleted }
            )

            tabGroup.copy(page = newPage)
        }
    }
}