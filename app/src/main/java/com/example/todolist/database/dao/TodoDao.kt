package com.example.todolist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.database.entity.TodoEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoCollection(todoCollection: TodoCollection)

    @Query("SELECT * FROM todo_entity WHERE collection_id = :collectionId")
    suspend fun getTodos(collectionId: Int): List<TodoEntity>

    @Query("SELECT * FROM todo_collection WHERE id = :id")
    suspend fun getTodoCollection(id: Int): List<TodoCollection>

@Query("UPDATE todo_entity SET is_completed = :isCompleted WHERE id = :id")
suspend fun updateTodoCompleted(id: Int, isCompleted: Boolean)

@Query("UPDATE todo_entity SET is_favorite = :isFavorite WHERE id = :id")
suspend fun updateTodoFavorite(id: Int, isFavorite: Boolean)

@Query("UPDATE todo_collection SET title = :title WHERE id = :id")
suspend fun updateTodoCollection(id: Int, title: String)


@Query("DELETE FROM todo_entity WHERE id = :id")
suspend fun deleteTodo(id: Int)

@Query("DELETE FROM todo_collection WHERE id = :id")
suspend fun deleteTodoCollection(id: Int)

@Update
suspend fun updateTodo(todo: TodoEntity)

@Update
suspend fun updateTodoCollection(todoCollection: TodoCollection)



}