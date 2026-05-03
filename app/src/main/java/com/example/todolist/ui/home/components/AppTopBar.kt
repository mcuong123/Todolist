package com.example.todolist.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar() {
    Box(modifier = Modifier.fillMaxSize().height(52.dp).padding(horizontal = 12.dp))
    {
        Text(
            "Việc cần làm ", style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center)
        )

    }
}