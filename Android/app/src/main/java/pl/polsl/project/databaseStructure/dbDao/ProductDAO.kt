package pl.polsl.project.databaseStructure.dbDao

import android.arch.persistence.room.*
import pl.polsl.project.databaseStructure.dataStructure.Product

@Dao
interface ProductDAO {


    @Query("SELECT * FROM products")
    fun getAll(): List<Product>

    @Query("SELECT * FROM products WHERE id=:id")
    fun getProduct(id:Int): List<Product>

    @Query("SELECT id,expenseId,expenseConstraintId,guarrantyDate,name,prise FROM products WHERE expenseId=:expenseId")
    fun getProductFromExpense(expenseId: Int): List<Product>

    @Query("SELECT id,expenseId,expenseConstraintId,guarrantyDate,name,prise FROM products WHERE expenseConstraintId=:expenseConstraintId")
    fun getProductFromConstraintExpense(expenseConstraintId: Int): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product) : Long

    @Delete
    fun delete(product: Product)

}