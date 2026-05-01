package com.example.todolist.di

import android.app.Application
import android.content.Context
import com.example.todolist.database.AppDb
import com.example.todolist.database.dao.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTodoDao(appDb: AppDb): TodoDao {
        return appDb.todoDao()
    }

    @Provides
    // duy nhat 1 thuc the
    @Singleton
    fun provideAppDb(@ApplicationContext context: Context): AppDb {
        return AppDb.invoke(context)
    }

}