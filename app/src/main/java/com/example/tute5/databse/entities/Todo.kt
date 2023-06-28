package com.example.tute5.databse.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Todo(
    var item: String?  //?-nul store t odo string
){

    @PrimaryKey(autoGenerate = true)

    var id:Int? = null
}
