package com.example.todolist.doamin.UseCase

import com.example.todolist.repository.TodoRepo
import com.example.todolist.ui.home.state.TodoUiState
import java.util.Calendar
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repo: TodoRepo
) {
    suspend operator fun invoke(todo: TodoUiState) : TodoUiState? {
        val newTodo = todo.copy(isFavorite = !todo.isFavorite)

        val success = repo.updateTodoFavorite(newTodo.id, newTodo.isFavorite)
        if (!success) return null

        val now = Calendar.getInstance().timeInMillis
        return newTodo.copy(
            updatedAt = now,
            stringUpdateAt = Calendar.getInstance().time.toString()
        )
    }
}