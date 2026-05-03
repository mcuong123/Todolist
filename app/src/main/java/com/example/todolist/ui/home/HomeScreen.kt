package com.example.todolist.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.todolist.ui.home.components.AddTodoFab
import com.example.todolist.ui.home.components.AppTopBar


@Composable
fun HomeScreen(){


    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {AddTodoFab(onClick = ::oncClick)}) {
        innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

            AppTopBar()


        }

    }

}
fun oncClick() {
 Log.d("onClick", "onClick")

}