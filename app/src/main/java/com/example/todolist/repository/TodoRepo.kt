package com.example.todolist.repository

import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.database.entity.TodoEntity

interface TodoRepo {
    suspend fun getTodos(collectionId: Long): List<TodoEntity>
    suspend fun getTodoCollection(): List<TodoCollection>
    suspend fun updateTodoCompleted(id: Long, isCompleted: Boolean) : Boolean
    suspend fun updateTodoFavorite(id: Long, isFavorite: Boolean) : Boolean
    suspend fun updateTodo(todo: TodoEntity): Boolean
    suspend fun updateTodoCollection(todoCollection: TodoCollection): Boolean
    suspend fun deleteTodoCollectionById(id: Long): Boolean
    suspend fun addTodoCollection(title: String) : TodoCollection?
    suspend fun addTodo(title: String, collectionId: Long) : TodoEntity?
    suspend fun updateCollectionSortType(collectionId: Long, sortType: Int) : Boolean
}





