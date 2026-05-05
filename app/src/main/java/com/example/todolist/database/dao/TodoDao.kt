package com.example.todolist.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.todolist.database.entity.SortType
import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.database.entity.TodoEntity

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity):Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoCollection(todoCollection: TodoCollection):Long

    @Query("SELECT * FROM todo_entity WHERE collection_id = :collectionId")
    suspend fun getTodos(collectionId: Long?): List<TodoEntity>

    @Query("SELECT * FROM todo_collection ")
    suspend fun getTodoCollection(): List<TodoCollection>

@Query("UPDATE todo_entity SET is_completed = :isCompleted WHERE id = :id")
suspend fun updateTodoCompleted(id: Long?, isCompleted: Boolean) : Int

@Query("UPDATE todo_entity SET is_favorite = :isFavorite WHERE id = :id")
suspend fun updateTodoFavorite(id: Long?, isFavorite: Boolean): Int

@Query("UPDATE todo_collection SET title = :title WHERE id = :id")
suspend fun updateTodoCollection(id: Long, title: String): Int


@Query("DELETE FROM todo_entity WHERE id = :id")
suspend fun deleteTodo(id: Long): Int

@Query("DELETE FROM todo_collection WHERE id = :id")
suspend fun deleteTodoCollectionById(id: Long):Int

@Update
suspend fun updateTodo(todo: TodoEntity): Int

@Update
suspend fun updateTodoCollection(todoCollection: TodoCollection): Int

@Delete
suspend fun deleteTodoCollection(todoCollection: TodoCollection): Int


@Query("UPDATE todo_collection SET sort_type = :sortType WHERE id = :collectionId")
suspend fun updateTodoCollectionSortType(collectionId: Long?, sortType: SortType): Int

}




