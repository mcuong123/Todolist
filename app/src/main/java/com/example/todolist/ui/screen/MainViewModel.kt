package com.example.todolist.ui.screen

import androidx.lifecycle.ViewModel
import com.example.todolist.repository.TodoRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoRepo: TodoRepo
)    : ViewModel() {

}