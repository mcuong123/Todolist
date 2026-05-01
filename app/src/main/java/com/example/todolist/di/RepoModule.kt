package com.example.todolist.di

import androidx.compose.ui.tooling.preview.Preview
import com.example.todolist.database.dao.TodoDao
import com.example.todolist.repository.ToDoRepoImp
import com.example.todolist.repository.TodoRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideTodoRepo(todoDao: TodoDao): TodoRepo {
        return ToDoRepoImp(todoDao)

    }
}