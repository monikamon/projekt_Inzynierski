package com.example.monika.inzynierka.DataStructure.DAOs

import android.arch.persistence.room.*
import com.example.monika.inzynierka.DataStructure.ConstrantExpense

@Dao
interface ConstrantExpenseDAO {

    @Query("SELECT * FROM constrantExpenses")
    fun getAll(): List<ConstrantExpense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(constrantExpense: ConstrantExpense) : Long

    @Delete
    fun delete(constrantExpense: ConstrantExpense)

}