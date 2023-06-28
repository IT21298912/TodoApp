package com.example.tute5.databse.repositories

import com.example.tute5.databse.TodoDatabase
import com.example.tute5.databse.entities.Todo

class TodoRepositories (

    private val db:TodoDatabase
        ){

    suspend fun insert(todo: Todo) = db.getTodoDao().insertTodo(todo)
    suspend fun delete(todo: Todo) = db.getTodoDao().delete(todo)
    suspend fun getAlltodos() = db.getTodoDao().getAllTodos()
}