package com.example.todolist.repository

import android.icu.util.Calendar
import com.example.todolist.database.dao.TodoDao
import com.example.todolist.database.entity.SortType
import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.database.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ToDoRepoImp(private val todoDao: TodoDao) : TodoRepo {

    override suspend fun addTodo(
        title: String,
        collectionId: Long?
    ): TodoEntity? = withContext(Dispatchers.IO) {
        val now = Calendar.getInstance().timeInMillis
        val todo = TodoEntity(
            title = title,
            isCompleted = false,
            isFavorite = false,
            collectionId = collectionId,
            updatedAt = now,
            createdAt = now
        )
        val id = todoDao.insertTodo(todo)
        if (id > 0) {
            todo.copy(id = id)
        } else {
            null
        }
    }

    override suspend fun updateTodoFavorite(
        id: Long?,
        isFavorite: Boolean
    ): Boolean = withContext(Dispatchers.IO) {
       todoDao.updateTodoFavorite(id, isFavorite) > 0

    }

    override suspend fun addTodoCollection(title: String): TodoCollection? =
        withContext(Dispatchers.IO) {
            val now = Calendar.getInstance().timeInMillis
            val todoCollection = TodoCollection(
                title = title,
                updatedAt = now,
                createdAt = now,
                sortType = SortType.CREATED_DATE.value
            )
            val id = todoDao.insertTodoCollection(todoCollection)
            if (id > 0) {
                todoCollection.copy(id = id)
            } else {
                null
            }

        }

    override suspend fun deleteTodoCollectionById(id: Long): Boolean {
       return withContext(Dispatchers.IO) {
           todoDao.deleteTodoCollectionById(id) > 0
       }
    }

    override suspend fun getTodoCollection(): List<TodoCollection> = withContext(Dispatchers.IO) {
        todoDao.getTodoCollection()
    }

    override suspend fun getTodos(collectionId: Long?): List<TodoEntity> =
        withContext(Dispatchers.IO) {
            todoDao.getTodos(collectionId)
        }

    override suspend fun updateCollectionSortType(
        collectionId: Long?,
        sortType: SortType
    ): Boolean {
       return withContext(Dispatchers.IO) {
           todoDao.updateTodoCollectionSortType(collectionId, sortType) > 0
       }
    }

    override suspend fun updateTodo(todo: TodoEntity) = withContext(Dispatchers.IO) {
        todoDao.updateTodo(todo) > 0
    }

    override suspend fun updateTodoCollection(todoCollection: TodoCollection): Boolean = withContext(Dispatchers.IO) {
    todoDao.updateTodoCollection(todoCollection) > 0
    }

    override suspend fun updateTodoCompleted(
        id: Long?,
        isCompleted: Boolean
    ) = withContext(Dispatchers.IO) {
        todoDao.updateTodoCompleted(id, isCompleted) > 0

    }


}