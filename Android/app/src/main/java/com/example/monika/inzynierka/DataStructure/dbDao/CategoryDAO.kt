package com.example.monika.inzynierka.DataStructure.dbDao

import android.arch.persistence.room.*
import com.example.monika.inzynierka.DataStructure.Category

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    @Query("SELECT * FROM categories where id=:id")
    fun getCategory(id:Int): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category) : Long

    @Delete
    fun delete(category: Category)

}