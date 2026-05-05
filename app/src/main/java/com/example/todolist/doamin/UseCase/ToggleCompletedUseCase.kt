package com.example.todolist.doamin.UseCase

import com.example.todolist.repository.TodoRepo
import com.example.todolist.ui.home.state.TodoUiState
import java.util.Calendar
import javax.inject.Inject

class ToggleCompletedUseCase @Inject constructor(
    private val repo: TodoRepo
) {
    suspend operator fun invoke(todo: TodoUiState) : TodoUiState? {
        val newTodo = todo.copy(isCompleted = !todo.isCompleted)
        val success = repo.updateTodoCompleted(newTodo.id, newTodo.isCompleted)
        if (!success) return null
        val now = Calendar.getInstance().timeInMillis
        return newTodo.copy(
            updatedAt = now,
            stringUpdateAt = Calendar.getInstance().time.toString()
        )
    }

}