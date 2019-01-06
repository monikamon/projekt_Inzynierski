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

@Database(entities = [Category::class, ConstrantExpense::class, Expense::class, Product::class], version=1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class DatabaseRoom:RoomDatabase(){

    //klasa abstrakcyjna, która odpowiada za komunikację użytkownika z baza danych
    abstract fun categoryDAO():CategoryDAO
    abstract fun constrantExpenseDAO(): ConstrantExpenseDAO
    abstract fun expenseDAO():ExpenseDAO
    abstract fun productDAO():ProductDAO

    companion object {
        private var INSTANCE: DatabaseRoom? = null

        //pobranie informacji zapisanych w bazie danych (wszystkich)
        fun getAppDataBase(context: Context? = null): DatabaseRoom? {
            if (INSTANCE == null) {
                synchronized(DatabaseRoom::class) {
                    INSTANCE = Room.databaseBuilder(context!!.applicationContext, DatabaseRoom::class.java, "Domostwo_Room_Database").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        //funckaj ktora usuwa kategorię z bazy danych i sprawdza, czy do danej kategorii, nie są przypisane wydatki
        fun deleteCategory(categoryName: Category): Boolean {

            val listExpense: List<Expense> = INSTANCE!!.expenseDAO().getExpanseFromCategory(categoryName.id!!)
            val listConstrantExpense: List<ConstrantExpense> = INSTANCE!!.constrantExpenseDAO().getExpanseFromCategory(categoryName.id!!)

            if (listExpense.isEmpty() && listConstrantExpense.isEmpty()) {

                INSTANCE!!.categoryDAO().delete(categoryName)
                return true
            }

            return false
        }

        //funckaj która dodaje kategorię do bazy danych
        fun addCategory(category: Category): Boolean {

            val list: List<Category> = INSTANCE!!.categoryDAO().getAll()

            for (element in list) {

                if (category.name.toUpperCase() == element.name.toUpperCase()) {

                    return false
                }
            }

            category.id = INSTANCE!!.categoryDAO().insert(category).toInt()
            return true
        }

        //usunięcie wydatku oraz wszystkich produktów z bazy danych
        fun deleteExpenseWithProducts(expense: Expense) {


            var getProducts = INSTANCE!!.productDAO().getProductFromExpense(expense.id!!)

            if(expense is ConstrantExpense)
                getProducts = INSTANCE!!.productDAO().getProductFromConstraintExpense(expense.id!!)

            for (element in getProducts) {

                INSTANCE!!.productDAO().delete(element)
            }

            if (expense is ConstrantExpense) {
                INSTANCE!!.constrantExpenseDAO().delete(expense)
            } else {
                INSTANCE!!.expenseDAO().delete(expense)
            }
        }

    }

}