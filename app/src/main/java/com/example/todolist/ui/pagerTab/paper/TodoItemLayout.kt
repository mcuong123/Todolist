package com.example.todolist.ui.pagerTab.paper

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.todolist.ui.home.delegae.TodoDelegate
import com.example.todolist.ui.home.state.TodoUiState
import com.example.todolist.ui.pagerTab.items.itemBgColor

@Composable
fun LazyItemScope.TodoItemLayout(state: TodoUiState, todoDelegate: TodoDelegate) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable {}
            .background(color = itemBgColor)
            .animateItem(
                tween(easing = LinearEasing),
                tween(easing = LinearEasing),
                tween(easing = LinearEasing)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = state.isCompleted,
            onCheckedChange = {
                todoDelegate.invertTodoCompleted(state)
            }
        )
        Column(
            modifier = Modifier.weight(1.0f).wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = state.title,
                modifier = Modifier.padding(horizontal = 4.dp),
                textDecoration = TextDecoration.LineThrough.takeIf { state.isCompleted }
            )
            if (state.isCompleted) {
                Text(text = "Completed: ${state.stringUpdateAt}", modifier = Modifier.padding(horizontal = 4.dp),)
            }
        }
        if (!state.isCompleted) {
            Icon(
                imageVector = if (state.isFavorite) {
                    Icons.Default.Star
                } else {
                    Icons.Default.StarBorder
                }, contentDescription = "Favorite",
                tint = if (state.isFavorite) Color.Yellow else Color.Gray,
                modifier = Modifier.clickable {
                    todoDelegate.invertTodoFavorite(state)
                }.padding(8.dp)
            )
        }
        }


}