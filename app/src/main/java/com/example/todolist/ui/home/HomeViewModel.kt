package com.example.todolist.ui.home

import androidx.lifecycle.ViewModel
import com.example.todolist.repository.TodoRepo
import com.example.todolist.ui.home.state.TodoGroupUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

const val ID_ADD_NEW_LIST = -999L
const val ID_FAVORITE_LIST = -1000L
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rep: TodoRepo
) : ViewModel(){

    private val _listTodoGroup : MutableStateFlow<List<TodoGroupUiState>> = MutableStateFlow(emptyList())
    val listTodoGroup = _listTodoGroup.asStateFlow()



}