package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.monika.inzynierka.DataStructure.*
import kotlinx.android.synthetic.main.activity_list_expense.*

class MainScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        DatabaseRoom.getAppDataBase(this)

        if(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAll().size==0) {
            for (i in 0 until 4) {

               var idexpanse = DatabaseRoom.getAppDataBase()!!.expenseDAO().insert(Expense(i.toString() + ".2.2001", 34.88 * i, "auto" + i.toString())).toInt()
               var prod: Product= Product((i*37).toString()+"botki", (i*13.95))
               prod.expenseId = idexpanse
                DatabaseRoom.getAppDataBase()!!.productDAO().insert(prod)
            }
            for (i in 0 until 3) {

                var idexpanse = DatabaseRoom.getAppDataBase()!!.expenseDAO().insert(ConstrantExpense(ConstrantExpenseTime.EVERYDAY, i.toString() + ".2.1998", 34.88 * i, "auto" + i.toString())).toInt()
            }
        }

    }


    fun showStatistics(view: View){

        val intent=Intent(this, StatisticsActivity::class.java)
        startActivity(intent)

    }

    fun showExpense(view: View){

        val intent=Intent(this, ListExpenseActivity::class.java)
        startActivity(intent)
    }
}

