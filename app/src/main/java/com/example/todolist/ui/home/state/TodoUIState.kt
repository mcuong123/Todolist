package com.example.todolist.ui.home.state

import com.example.todolist.database.entity.TodoEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TodoUiState(
    val id: Long?,
    val title: String,
    val isFavorite: Boolean,
    val isCompleted: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val collectionId: Long,
    val stringUpdateAt: String
)

fun TodoEntity.toTodoUIState(): TodoUiState {
    return TodoUiState(
        id = this.id,
        title = this.title,
        isFavorite = this.isFavorite,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        collectionId = this.collectionId,
        stringUpdateAt = this.updatedAt.millisToDateString()

    )
}

fun Long.millisToDateString(): String {
    return SimpleDateFormat("EEE,dd MMM yyyy", Locale.getDefault()).format(Date(this)).toString()
}