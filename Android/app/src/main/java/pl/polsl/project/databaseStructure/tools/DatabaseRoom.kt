package pl.polsl.project.databaseStructure.tools

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.dataStructure.Product
import pl.polsl.project.databaseStructure.dbDao.CategoryDAO
import pl.polsl.project.databaseStructure.dbDao.ConstrantExpenseDAO
import pl.polsl.project.databaseStructure.dbDao.ExpenseDAO
import pl.polsl.project.databaseStructure.dbDao.ProductDAO

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

        fun deleteCategory(categoryName: Category):Boolean{

            var listExpense:List<Expense> = INSTANCE!!.expenseDAO().getExpanseFromCategory(categoryName.id!!)
            var listConstrantExpense:List<ConstrantExpense> = INSTANCE!!.constrantExpenseDAO().getExpanseFromCategory(categoryName.id!!)

            if(listExpense.isEmpty() && listConstrantExpense.isEmpty()){

                INSTANCE!!.categoryDAO().delete(categoryName)
                return true
            }

            return false
        }

        fun addCategory(category: Category):Boolean{

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