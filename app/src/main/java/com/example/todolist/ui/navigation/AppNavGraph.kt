package com.example.todolist.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.ui.home.HomeScreen
import com.example.todolist.ui.theme.TodolistTheme

@Composable
fun AppNavGraph() {
    TodolistTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            startDestination = NavScreen.HOME.route
        ) {
            composable(route = NavScreen.HOME.route) {
                HomeScreen()
            }
        }
    }
}



enum class NavScreen(val route: String) {
    HOME("home"),
    TASK("task/{taskId}"),
    COLLECTION("collection/{collectionId}"),
    SETTINGS("settings")
}