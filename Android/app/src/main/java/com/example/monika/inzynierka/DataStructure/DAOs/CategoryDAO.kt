package com.example.monika.inzynierka.DataStructure.DAOs

import android.arch.persistence.room.*
import com.example.monika.inzynierka.DataStructure.Category

@Dao
interface CategoryDAO {

    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category) : Long

    @Delete
    fun delete(category: Category)

}