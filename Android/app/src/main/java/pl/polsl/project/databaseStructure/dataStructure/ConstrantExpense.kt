package pl.polsl.project.databaseStructure.dataStructure

import android.arch.persistence.room.Entity
import android.graphics.Bitmap
import pl.polsl.project.databaseStructure.tools.ConstrantExpenseTime
import java.io.Serializable

@Entity(tableName = "constrantExpenses")
class ConstrantExpense: Expense,  Serializable{

    // klasa odpowiadająca za stworzenie tabeli stałego wydatku w bazie danych


    //dziedziczy po receipt
    //data do której należy zapłacić dany wydatek (co ile się płaci)
    var timeToPayExpense: ConstrantExpenseTime

    //wywołanie konstruktora tej klasy i klasy nadrzędnej
    constructor(timeToPayExpense: ConstrantExpenseTime, shoppingDate:String, prise:Double, expenseName:String, receiptPhoto: Bitmap?=null):super(shoppingDate, prise, expenseName, receiptPhoto){

        this.timeToPayExpense=timeToPayExpense
    }

    constructor():super(){

        this.timeToPayExpense= ConstrantExpenseTime.EVERYDAY


    }

}