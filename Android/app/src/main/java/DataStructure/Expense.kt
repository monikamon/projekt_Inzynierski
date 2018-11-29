package DataStructure

import android.graphics.Bitmap
import android.printservice.PrintService
import java.io.Serializable

open class Expense: Serializable{


    val list = ArrayList<Product>()

    var shoppingDate:String
    var receiptPhoto:Bitmap?
    var prise:Double

    //konstruktor bezargumentowy
    constructor(){

        this.shoppingDate=""
        this.receiptPhoto=null
        this.prise=0.0
    }

    constructor(shoppingDate:String, prise:Double, receiptPhoto:Bitmap?=null){

        this.shoppingDate=shoppingDate
        this.receiptPhoto=receiptPhoto
        this.prise=prise
    }



}