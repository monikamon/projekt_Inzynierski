package com.example.monika.inzynierka.DataStructure

import android.arch.persistence.room.*
import android.graphics.Bitmap
import com.example.monika.inzynierka.DataStructure.tools.BitMapChanger
import java.io.Serializable

@Entity(tableName = "products")
class Product: Serializable, BitMapChanger {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ForeignKey(entity = Expense::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("expenseId"))
    var expenseId :Int? = null

    var guarrantyDate:String?
    var name:String
    var prise:Double


    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    override var photoByteArray:ByteArray? = null

    @Ignore
    override var photo:Bitmap?=null

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