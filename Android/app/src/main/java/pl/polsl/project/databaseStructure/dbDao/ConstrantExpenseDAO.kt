package pl.polsl.project.databaseStructure.dbDao

import android.arch.persistence.room.*
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense

@Dao
interface ConstrantExpenseDAO {

    //komunikacja z bazą danych z tabelą stałego wydatku  za pomoca zapytań SQL

    @Query("SELECT * FROM constrantExpenses")
    fun getAll(): List<ConstrantExpense>

    @Query("SELECT id,categoryId,shoppingDate,price,expenseName,timeToPayExpense FROM constrantExpenses")
    fun getAllWithoutPhoto(): List<ConstrantExpense>

    @Query("SELECT * FROM constrantExpenses WHERE id=:id")
    fun getConstrantExpense(id:Int): List<ConstrantExpense>

    @Query("SELECT id,categoryId,shoppingDate,price,expenseName,timeToPayExpense FROM constrantExpenses WHERE categoryId=:categoryId")
    fun getExpanseFromCategory(categoryId: Int): List<ConstrantExpense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(constrantExpense: ConstrantExpense) : Long

    @Delete
    fun delete(constrantExpense: ConstrantExpense)

}