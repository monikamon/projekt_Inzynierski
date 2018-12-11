package pl.polsl.project.databaseStructure.dbDao

import android.arch.persistence.room.*
import pl.polsl.project.databaseStructure.dataStructure.Expense

@Dao
interface ExpenseDAO {

    @Query("SELECT id,categoryId,shoppingDate,price,expenseName FROM expenses")
    fun getAllWithoutPhoto(): List<Expense>

    @Query("SELECT * FROM expenses WHERE id=:id")
    fun getExpense(id:Int): List<Expense>

    @Query("SELECT id,categoryId,shoppingDate,price,expenseName FROM expenses WHERE categoryId=:categoryId")
    fun getExpanseFromCategory(categoryId: Int): List<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expanse: Expense) : Long

    @Delete
    fun delete(expanse: Expense)

}