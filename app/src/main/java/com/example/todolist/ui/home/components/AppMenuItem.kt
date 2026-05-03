package com.example.todolist.ui.home.components

data class AppMenuItem(
    val title: String,
    val action: () -> Unit
)