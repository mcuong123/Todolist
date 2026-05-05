package com.example.todolist.ui.pagerTab.paper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.home.delegae.TodoDelegate
import com.example.todolist.ui.home.state.TodoGroupUiState
import com.example.todolist.ui.pagerTab.items.activeTasksHeader
import com.example.todolist.ui.pagerTab.items.bottomCorner
import com.example.todolist.ui.pagerTab.items.emptyState
import com.example.todolist.ui.pagerTab.items.listTodoItems
import com.example.todolist.ui.pagerTab.items.spacer
import com.example.todolist.ui.pagerTab.items.topCorner

@Composable
fun TodoListPage(state: TodoGroupUiState,todoDelegate: TodoDelegate) {

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(12.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        topCorner()
        activeTasksHeader("activeTasksHeader", state, todoDelegate)
        emptyState("empty", state.page)
        listTodoItems("active", state.page.activeTodoList, todoDelegate)
        bottomCorner()
        spacer(24)

        topCorner()
        listTodoItems("completed", state.page.completedTodoList, todoDelegate)
        bottomCorner()



    }
}