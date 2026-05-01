package com.example.todolist.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.todolist.database.dao.TodoDao
import com.example.todolist.database.entity.TodoCollection
import com.example.todolist.database.entity.TodoEntity


private const val DATABASE_NAME = "todo_db"
private const val DATABASE_VERSION = 1

@Database(
    entities = [TodoEntity::class, TodoCollection::class],
    version = DATABASE_VERSION,
    exportSchema = true
)
abstract class AppDb(): RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object{
            private var instance: AppDb? = null

            operator fun invoke(context: Context): AppDb {
                return instance ?: synchronized(this) {
                    instance ?: buildDatabase(context).also { instance = it }
        }


            }
        fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            AppDb::class.java,
            DATABASE_NAME
        ).addMigrations(MIGRATION_1_2).build()
}

}
private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE todo_entity ADD COLUMN sort_type INTEGER NOT NULL DEFAULT 0")
    }

}

