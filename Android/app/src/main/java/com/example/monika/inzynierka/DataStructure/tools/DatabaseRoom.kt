package com.example.monika.inzynierka.DataStructure.tools

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.monika.inzynierka.DataStructure.Category
import com.example.monika.inzynierka.DataStructure.ConstrantExpense
import com.example.monika.inzynierka.DataStructure.Expense
import com.example.monika.inzynierka.DataStructure.Product
import com.example.monika.inzynierka.DataStructure.dbDao.CategoryDAO
import com.example.monika.inzynierka.DataStructure.dbDao.ConstrantExpenseDAO
import com.example.monika.inzynierka.DataStructure.dbDao.ExpenseDAO
import com.example.monika.inzynierka.DataStructure.dbDao.ProductDAO

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

        fun deleteCategory(categoryName:Category):Boolean{

            var listExpense:List<Expense> = INSTANCE!!.expenseDAO().getExpanseFromCategory(categoryName.id!!)
            var listConstrantExpense:List<ConstrantExpense> = INSTANCE!!.constrantExpenseDAO().getExpanseFromCategory(categoryName.id!!)

            if(listExpense.isEmpty() && listConstrantExpense.isEmpty()){

                INSTANCE!!.categoryDAO().delete(categoryName)
                return true
            }

            return false
        }

        fun addCategory(category:Category):Boolean{

            var list: List<Category>
            list = INSTANCE!!.categoryDAO().getAll()

            for(element in list){

                if(category.name.toUpperCase()==element.name.toUpperCase()){

                    return false
                }
            }

            category.id = INSTANCE!!.categoryDAO().insert(category).toInt()
            return true
        }

        fun deleteExpenseWithProducts(expense: Expense){



            var getProducts = INSTANCE!!.productDAO().getProductFromExpense(expense.id!!)

            for(element in getProducts){

                INSTANCE!!.productDAO().delete(element)
            }

            if(expense is ConstrantExpense){
                INSTANCE!!.constrantExpenseDAO().delete(expense)
            }else {
                INSTANCE!!.expenseDAO().delete(expense)
            }
        }
    }

}