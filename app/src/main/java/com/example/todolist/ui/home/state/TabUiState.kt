package com.example.todolist.ui.home.state

import com.example.todolist.database.entity.SortType
import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.database.entity.toSortType


data class TabUiState(
    val id: Long?,
    val title: String,
    val sortType: SortType
)

fun TodoCollection.toTabUiState() : TabUiState {
    return TabUiState(
        id = this.id,
        title = this.title,
        sortType = this.sortType.toSortType()
    )

}