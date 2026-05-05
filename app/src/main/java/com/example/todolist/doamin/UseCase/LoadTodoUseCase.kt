package com.example.todolist.doamin.UseCase

import com.example.todolist.database.entity.TodoEntity
import com.example.todolist.doamin.module.TodoWithCollection
import com.example.todolist.repository.TodoRepo
import com.example.todolist.ui.home.state.TodoUiState
import com.example.todolist.ui.home.state.toTodoUIState
import javax.inject.Inject

class LoadTodoUseCase @Inject constructor(
private val repo: TodoRepo
) {
    suspend operator fun invoke(): List<TodoWithCollection> {
        val collection = repo.getTodoCollection()
        return collection.map { collection ->
            val todo: List<TodoUiState> = repo.getTodos(collection.id).map { it.toTodoUIState() }

            TodoWithCollection(
                collection = collection,
                todos = todo
            )
        }
    }
}



