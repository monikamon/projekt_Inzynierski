package com.example.monika.inzynierka.DataStructure

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "categories")
class Category: Serializable{

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var name:String

    constructor(name:String){

        this.name=name
    }

}