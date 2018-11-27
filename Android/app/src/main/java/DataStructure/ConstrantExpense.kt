package DataStructure

import android.graphics.Bitmap
import java.io.Serializable

class ConstrantExpense: Expense,  Serializable{


    //dziedziczy po receipt
    var timeToPayExpense: ConstrantExpenseTime

    //wywołanie konstruktora tej klasy i klasy nadrzędnej
    constructor(timeToPayExpense: ConstrantExpenseTime, shoppingDate:String, prise:Double, receiptPhoto: Bitmap?=null):super(shoppingDate, prise, receiptPhoto){

        this.timeToPayExpense=timeToPayExpense
    }

    constructor():super(){

        this.timeToPayExpense=ConstrantExpenseTime.EVERYDAY
    }

}