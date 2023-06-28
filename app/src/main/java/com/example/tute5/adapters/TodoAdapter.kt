package com.example.tute5.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tute5.R
import com.example.tute5.databse.TodoDatabase
import com.example.tute5.databse.entities.Todo
import com.example.tute5.databse.repositories.TodoRepositories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    lateinit var data:List<Todo>
    lateinit var context: Context

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){

        val cbTod:CheckBox
        val ivDel:ImageView

        init {
            cbTod = view.findViewById(R.id.cbTodo)
            ivDel = view.findViewById(R.id.ivDelete)

        }
    }

    fun setData(data:List<Todo>,context: Context){

        this.data = data
        this.context = context

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.cbTod.text = data[position].item

        holder.ivDel.setOnClickListener{

            if (holder.cbTod.isChecked){
                val repository = TodoRepositories(TodoDatabase.getInstance(context))
                holder.cbTod.isChecked = false
                holder.ivDel.setImageResource(R.drawable.delete_icon_selected)

                CoroutineScope(Dispatchers.IO).launch {

                    repository.delete(data[position])
                    val data = repository.getAlltodos()

                    withContext(Dispatchers.Main){

                        setData(data,context)
                        holder.ivDel.setImageResource(R.drawable.delete_icon)
                    }

                }



            }else{

                Toast.makeText(context,"cannot delete unmarked Todo items",Toast.LENGTH_LONG)

            }

        }
    }
}