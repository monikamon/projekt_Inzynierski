package pl.polsl.project.databaseStructure.dbDao

import android.arch.persistence.room.*
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense

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