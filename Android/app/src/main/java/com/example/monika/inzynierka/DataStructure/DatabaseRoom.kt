package com.example.monika.inzynierka.DataStructure

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.monika.inzynierka.DataStructure.DAOs.CategoryDAO
import com.example.monika.inzynierka.DataStructure.DAOs.ConstrantExpenseDAO
import com.example.monika.inzynierka.DataStructure.DAOs.ExpenseDAO
import com.example.monika.inzynierka.DataStructure.DAOs.ProductDAO
import com.example.monika.inzynierka.DataStructure.tools.Converter

@Database(entities = arrayOf(Category::class, ConstrantExpense::class, Expense::class, Product::class), version=1)
@TypeConverters(Converter::class)
abstract class DatabaseRoom:RoomDatabase(){

    abstract fun categoryDAO():CategoryDAO
    abstract fun constrantExpenseDAO(): ConstrantExpenseDAO
    abstract fun expenseDAO():ExpenseDAO
    abstract fun productDAO():ProductDAO

    companion object {
        private var INSTANCE: DatabaseRoom? = null

        fun getAppDataBase(context: Context?=null): DatabaseRoom? {
            if (INSTANCE == null){
                synchronized(DatabaseRoom::class){
                    INSTANCE = Room.databaseBuilder(context!!.applicationContext, DatabaseRoom::class.java, "DatabaseRoom").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }

}