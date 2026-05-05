package com.example.todolist.doamin.module


import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.ui.home.state.TodoUiState

data class TodoWithCollection(
    val collection: TodoCollection,
    val todos: List<TodoUiState>
)