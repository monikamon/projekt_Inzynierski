package com.example.monika.inzynierka.DataStructure

import android.arch.persistence.room.Entity
import android.graphics.Bitmap
import java.io.Serializable

@Entity(tableName = "constrantExpenses")
class ConstrantExpense: Expense,  Serializable{

    //dziedziczy po receipt
    //data do której należy zapłacić dany wydatek (co ile się płaci)
    var timeToPayExpense: ConstrantExpenseTime

    //wywołanie konstruktora tej klasy i klasy nadrzędnej
    constructor(timeToPayExpense: ConstrantExpenseTime, shoppingDate:String, prise:Double, expenseName:String, receiptPhoto: Bitmap?=null):super(shoppingDate, prise, expenseName, receiptPhoto){

        this.timeToPayExpense=timeToPayExpense
    }

    constructor():super(){

        this.timeToPayExpense=ConstrantExpenseTime.EVERYDAY


    }

}