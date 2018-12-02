package com.example.monika.inzynierka.DataStructure.dbDao

import android.arch.persistence.room.*
import com.example.monika.inzynierka.DataStructure.ConstrantExpense

@Dao
interface ConstrantExpenseDAO {

    @Query("SELECT * FROM constrantExpenses")
    fun getAll(): List<ConstrantExpense>

    @Query("SELECT * FROM constrantExpenses WHERE categoryId=:categoryId")
    fun getExpanseFromCategory(categoryId: Int): List<ConstrantExpense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(constrantExpense: ConstrantExpense) : Long

    @Delete
    fun delete(constrantExpense: ConstrantExpense)

}