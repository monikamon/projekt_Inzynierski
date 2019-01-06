package pl.polsl.project.databaseStructure.dataStructure

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Suppress("ConvertSecondaryConstructorToPrimary")
@Entity(tableName = "categories")
class Category: Serializable{

    // klasa odpowiadająca za stworzenie tabeli kategorii w bazie danych
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    var name:String

    constructor(name:String){

        this.name=name
    }

}