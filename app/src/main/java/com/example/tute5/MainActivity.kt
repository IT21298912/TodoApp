package com.example.tute5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tute5.adapters.TodoAdapter
import com.example.tute5.databse.TodoDatabase
import com.example.tute5.databse.entities.Todo
import com.example.tute5.databse.repositories.TodoRepositories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = TodoRepositories(TodoDatabase.getInstance(this))

        val recyclerView:RecyclerView = findViewById(R.id.rvTodoList)
        val ui = this
        val adapter = TodoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        val btnAddTodo = findViewById<Button>(R.id.btnAddTodo)
        btnAddTodo.setOnClickListener{
            displayDialog(repository,adapter)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAlltodos()
            adapter.setData(data,ui)
        }


    }


    fun displayDialog(repositories: TodoRepositories,adapter: TodoAdapter){

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Enter new todo item ")
        builder.setMessage("Enter the todo item below ")


        val input = EditText(this)
        input.inputType=InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("Ok"){ dialog,which ->

            val item = input.text.toString()

            CoroutineScope(Dispatchers.IO).launch {

                repositories.insert(Todo(item))
                val data = repositories.getAlltodos()

                runOnUiThread{
                    adapter.setData(data,this@MainActivity)
                }

            }

        }

        builder.setNegativeButton("cancel"){dialog,which ->
            dialog.cancel()

        }

        val alertDialog = builder.create()
        alertDialog.show()



    }
}