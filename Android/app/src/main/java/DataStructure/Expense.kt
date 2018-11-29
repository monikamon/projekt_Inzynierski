package DataStructure

import android.graphics.Bitmap
import java.io.Serializable

open class Expense: Serializable{


    val listOfProducts = ArrayList<Product>()

    var shoppingDate:String
    var receiptPhoto:Bitmap?
    var price:Double

    //konstruktor bezargumentowy
    constructor(){

        this.shoppingDate=""
        this.receiptPhoto=null
        this.price=0.0
    }

    constructor(shoppingDate:String, prise:Double, receiptPhoto:Bitmap?=null){

        this.shoppingDate=shoppingDate
        this.receiptPhoto=receiptPhoto
        this.price=prise
    }



}