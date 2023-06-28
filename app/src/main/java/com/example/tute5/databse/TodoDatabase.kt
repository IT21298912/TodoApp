package com.example.tute5.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tute5.databse.daos.TodoDao
import com.example.tute5.databse.entities.Todo

//version-update
@Database(entities = [Todo::class], version = 1)         //Todod -Entity(table ), In apllication there can be multiple tables there for []
abstract class TodoDatabase:RoomDatabase() {         //abstract class


    abstract fun getTodoDao(): TodoDao

    //--THIS PART IS SAME FOR ALL APP

    companion object{   //static obj
        @Volatile               //use memory
        private var INSTANCE: TodoDatabase? = null    //create instance
        fun getInstance(context: Context):TodoDatabase {
            synchronized(this){                         //make sure syncro
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_db"              //name of the db
                ).build().also {
                    INSTANCE = it
                }



            }




        }


    }

    //---------------------//

}