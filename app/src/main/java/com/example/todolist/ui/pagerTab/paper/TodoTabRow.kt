package com.example.todolist.ui.pagerTab.paper

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.home.state.TabUiState


@Composable
fun TodoTabRow(
    selectedTabIndex: Int,
    listTabs: List<TabUiState>,
    onTabSelected: (Int) -> Unit
) {

    if (listTabs.size > 3) {
        PrimaryScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 0.dp,
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex)
                )

            }
        ) {
            // tabs
        }
    } else {
        PrimaryTabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.fillMaxWidth(),
            indicator = {
                TabRowDefaults.PrimaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex)
                )

            }
        ) {
            // tabs
        }
    }
}