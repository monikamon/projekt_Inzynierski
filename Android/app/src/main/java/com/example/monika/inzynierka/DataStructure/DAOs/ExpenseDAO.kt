package com.example.monika.inzynierka.DataStructure.DAOs

import android.arch.persistence.room.*
import com.example.monika.inzynierka.DataStructure.Expense

@Dao
interface ExpenseDAO {

    @Query("SELECT * FROM expenses")
    fun getAll(): List<Expense>

    @Query("SELECT * FROM expenses WHERE categoryId=:categoryId")
    fun getExpanseFromCategory(categoryId: Int): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expanse: Expense) : Long

    @Delete
    fun delete(expanse: Expense)

}