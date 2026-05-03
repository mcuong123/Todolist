package com.example.todolist.ui.home.event

import com.example.todolist.ui.home.components.AppMenuItem

sealed class HomeEvent {
    data object RequestAddNewCollection : HomeEvent()
    data class RequestShowBottomSheetOptions(val list: List<AppMenuItem>) : HomeEvent()
}