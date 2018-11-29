package com.example.monika.inzynierka

import DataStructure.Data
import DataStructure.Expense
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.provider.ContactsContract
import android.view.View
import android.widget.EditText
import com.example.monika.inzynierka.R.layout.activity_statistics

class MainScreen : AppCompatActivity() {


    val data= Data()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        for(i in 0 until 7){
             var elem= Expense()
            elem.shoppingDate=i.toString()
            data.expenseList.add(elem)
            elem.prise=(i+3453/1000).toDouble()
            data.expenseList.add(elem)

        }

    }


    fun showStatistics(view: View){

        val intent=Intent(this, StatisticsActivity::class.java)
        startActivity(intent)

    }

    fun showExpense(view: View){

        val intent=Intent(this, ExpenseActivity::class.java)
        intent.putExtra("Dane", data)
        startActivity(intent)
    }
}

