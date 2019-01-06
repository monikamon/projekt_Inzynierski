package pl.polsl.project.databaseStructure.dataStructure

import android.arch.persistence.room.*
import android.graphics.Bitmap
import pl.polsl.project.databaseStructure.tools.interfaces.BitMapChanger
import java.io.Serializable

@Entity(tableName = "expenses")
open class Expense: Serializable, BitMapChanger {

// klasa odpowiadająca za stworzenie tabeli wydatku w bazie danych

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ForeignKey(entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"])
    var categoryId :Int? = null

    var shoppingDate:String

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    override var photoByteArray:ByteArray? = null

    var price:Double

    var expenseName:String

    //konstruktor bezargumentowy
    constructor(){

        this.shoppingDate=""
        this.price=0.0
        this.expenseName=""
    }

    constructor(shoppingDate:String, prise:Double, expenseName:String, receiptPhoto:Bitmap?=null){

        this.shoppingDate=shoppingDate
        this.setBitmapPhoto(receiptPhoto)
        this.price=prise
        this.expenseName=expenseName
    }



}