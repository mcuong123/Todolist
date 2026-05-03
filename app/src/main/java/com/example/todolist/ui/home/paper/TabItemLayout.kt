package com.example.todolist.ui.home.paper

import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.todolist.ui.home.state.TabUiState

@Composable
fun TabItemLayout(state: TabUiState, isSelected: Boolean, onTabSelected: () -> Unit) {
    Tab(text = {
        Text( state.title, color = Color.Unspecified)
//            state.title, color = when (state.id) {
//                ID_FAVORITE_LIST -> {
//                    Color.Yellow
//                }
//                ID_ADD_NEW_LIST -> {
//                    Color.Blue
//                }
//                else -> {
//                    Color.Unspecified
//                }
//            }

    }, selected = isSelected, onClick = onTabSelected)
}