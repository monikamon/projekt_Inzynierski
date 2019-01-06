package pl.polsl.project.databaseStructure.dbDao

import android.arch.persistence.room.*
import pl.polsl.project.databaseStructure.dataStructure.Category

@Dao
interface CategoryDAO {

    //komunikacja z bazą danych z tabelą kategorii za pomoca zapytań SQL
    @Query("SELECT * FROM categories")
    fun getAll(): List<Category>

    @Query("SELECT * FROM categories where id=:id")
    fun getCategory(id:Int): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category) : Long

    @Delete
    fun delete(category: Category)

}