@file:Suppress("DEPRECATION")

package pl.polsl.project.databaseStructure.tools.interfaces

import android.annotation.SuppressLint
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.tools.ConstrantExpenseTime
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import java.text.SimpleDateFormat
import java.util.*

interface ConstraintExpenseAdder {

    // klasa, która sprawdza datę stałego wydatku. Jeżeli jest data w której stały wydatek ma zostać opłacany, jest przepisywany do listy wydatku
    @SuppressLint("SimpleDateFormat")
    fun checkConstraintExpense() {

        // sprawdzenie, czy wydatek jest wydatkiem stałym
        val listOfConstrantExpense = DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAll()

        val df = SimpleDateFormat("dd/MM/yyyy")
        df.isLenient = false

        for (expense in listOfConstrantExpense) {
            var date: Date = df.parse(expense.shoppingDate)

                if(date.compareTo(Date()) != 1) {

                    while(date.compareTo(Date()) != 1){

                        val expenseToInsert = Expense()

                        expenseToInsert.categoryId = expense.categoryId
                        expenseToInsert.shoppingDate = df.format(date)
                        expenseToInsert.photoByteArray = expense.photoByteArray
                        expenseToInsert.price = expense.price
                        expenseToInsert.expenseName = expense.expenseName

                        expenseToInsert.id = DatabaseRoom.getAppDataBase()!!.expenseDAO().insert(expenseToInsert).toInt()

                        val listOfproducts = DatabaseRoom.getAppDataBase()!!.productDAO().getProductFromConstraintExpense(expense.id!!)

                        for(prod in listOfproducts){
                            prod.id=null
                            prod.expenseId = expenseToInsert.id
                            DatabaseRoom.getAppDataBase()!!.productDAO().insert(prod)
                        }

                        date = addAmountToDate(date,expense)
                    }

                    expense.shoppingDate = df.format(date)
                    DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().insert(expense)

                }
            }

    }

    //funkcja, która sprawdza częstotliwość wydatku stałego
    fun addAmountToDate(date:Date,conExp:ConstrantExpense): Date{
        val calendar = Calendar.getInstance()
        calendar.time = date
        when(conExp.timeToPayExpense){

            ConstrantExpenseTime.EVERYDAY -> calendar.add(Calendar.DAY_OF_YEAR,1)
            ConstrantExpenseTime.EVERY_WEEK -> calendar.add(Calendar.WEEK_OF_YEAR,1)
            ConstrantExpenseTime.EVERY_MONTH -> calendar.add(Calendar.MONTH,1)
            ConstrantExpenseTime.EVERY_TWO_MONTHS -> calendar.add(Calendar.MONTH,2)
            ConstrantExpenseTime.EVERY_THREE_MONTHS -> calendar.add(Calendar.MONTH,3)
            ConstrantExpenseTime.EVERY_SIX_MONTHS -> calendar.add(Calendar.MONTH,6)
            ConstrantExpenseTime.EVERY_YEAR -> calendar.add(Calendar.YEAR,1)
        }
        return calendar.time
    }

}
