package com.example.monika.inzynierka.DataStructure

import android.arch.persistence.room.*
import android.graphics.Bitmap
import com.example.monika.inzynierka.DataStructure.tools.BitMapChanger
import java.io.Serializable

@Entity(tableName = "expenses")
open class Expense: Serializable,BitMapChanger{

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ForeignKey(entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"))
    var categoryId :Int? = null

    var shoppingDate:String

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    override var photoByteArray:ByteArray? = null

    @Ignore
    override var photo:Bitmap?=null

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