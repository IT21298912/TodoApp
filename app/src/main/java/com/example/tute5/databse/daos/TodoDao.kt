package com.example.tute5.databse.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tute5.databse.entities.Todo

@Dao
interface TodoDao {

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("SELECT * From Todo")
    fun getAllTodos():List<Todo>
}