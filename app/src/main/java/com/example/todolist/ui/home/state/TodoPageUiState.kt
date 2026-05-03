package com.example.todolist.ui.home.state

data class TodoPageUiState(
    val activeTodoList : List<TodoUiState> =emptyList(),
    val completedTodoList : List<TodoUiState> = emptyList()
)