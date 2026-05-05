package com.example.todolist.ui.home.delegae

import com.example.todolist.ui.home.state.TodoUiState

interface TodoDelegate {
    fun invertTodoFavorite(todoUiState: TodoUiState) = Unit
    fun invertTodoCompleted(todoUiState: TodoUiState) = Unit
    fun addNewTodo(collectionId: Long, content: String) = Unit
    fun addNewTodoToCurrentCollection(content: String) = Unit
    fun updateCurrentCollectionId(collectionId: Long) = Unit
    fun currentCollectionId(): Long = -1L
    fun addNewCollection(title: String) = Unit
    fun requestAddNewCollection(): Unit = Unit
    fun requestUpdateCollection(collectionId: Long) = Unit
    fun requestSortTodo(collectionId: Long?) = Unit
}