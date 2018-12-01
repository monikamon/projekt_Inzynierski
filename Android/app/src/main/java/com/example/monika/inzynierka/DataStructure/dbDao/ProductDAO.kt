package com.example.monika.inzynierka.DataStructure.dbDao

import android.arch.persistence.room.*
import com.example.monika.inzynierka.DataStructure.Product

@Dao
interface ProductDAO {


    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE expenseId=:expenseId")
    fun getProductFromExpense(expenseId: Int): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product) : Long

    @Delete
    fun delete(product: Product)

}