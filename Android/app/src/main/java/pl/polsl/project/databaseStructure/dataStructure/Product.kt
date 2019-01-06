package pl.polsl.project.databaseStructure.dataStructure

import android.arch.persistence.room.*
import android.graphics.Bitmap
import pl.polsl.project.databaseStructure.tools.interfaces.BitMapChanger
import java.io.Serializable

@Suppress("unused")
@Entity(tableName = "products")
class Product: Serializable, BitMapChanger {

    // klasa odpowiadająca za stworzenie tabeli produktu w bazie danych

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ForeignKey(entity = Expense::class,
            parentColumns = ["id"],
            childColumns = ["expenseId"])
    var expenseId :Int? = null

    @ForeignKey(entity = Expense::class,
            parentColumns = ["id"],
            childColumns = ["expenseConstraintId"])
    var expenseConstraintId :Int? = null

    var guarrantyDate:String?
    var name:String
    var prise:Double


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    override var photoByteArray:ByteArray? = null

    constructor(){

        this.guarrantyDate=null
        this.name=""
        this.prise=0.0

    }
    constructor(name:String, prise:Double, guarrantyDate:String?=null, photoPicture:Bitmap?=null){

        this.guarrantyDate=guarrantyDate
        this.name=name
        this.prise=prise
        this.setBitmapPhoto(photoPicture)
    }



}