package com.example.todolist.ui.pagerTab.paper

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import com.example.todolist.ui.home.ID_ADD_NEW_LIST
import com.example.todolist.ui.home.delegae.TodoDelegate
import com.example.todolist.ui.home.state.TodoGroupUiState
import kotlinx.coroutines.launch

@Composable
fun PagerTabLayout(state: List<TodoGroupUiState>, todoDelegate: TodoDelegate) {

    var pageCount by remember { mutableIntStateOf(0) }
    var internalState by remember { mutableStateOf(state) }
    internalState = state
    val pagerState = rememberPagerState{pageCount}
    val scope = rememberCoroutineScope()

    pageCount = state.count {
        it.tab.id != ID_ADD_NEW_LIST
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.collect { index ->
            internalState.getOrNull(index)?.tab?.id?.let { todoDelegate.updateCurrentCollectionId(it) }

        }

    }

    TodoTabRow(
        selectedTabIndex = pagerState.currentPage,
        listTabs = state.map { it.tab  },
    onTabSelected = { index ->
        if((state.getOrNull(index)?.tab?.id ?: 0 )   == ID_ADD_NEW_LIST) {
            todoDelegate.requestAddNewCollection()

        } else {
            scope.launch {
                pagerState.animateScrollToPage(index)
            }
        }
    }
    )
    HorizontalPager(
        state = pagerState,
        key = {it},
        beyondViewportPageCount = 2,
        userScrollEnabled = true
    ) { pageIndex ->
        TodoListPage(state[pageIndex],todoDelegate)
    }

}