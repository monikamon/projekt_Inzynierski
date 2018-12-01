package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.monika.inzynierka.DataStructure.tools.DatabaseRoom
import com.example.monika.inzynierka.DataStructure.Expense
import kotlinx.android.synthetic.main.activity_list_expense.*

class ListExpenseActivity : AppCompatActivity() {

    var db : DatabaseRoom = DatabaseRoom.getAppDataBase()!!
    var expanses:ArrayList<Expense> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expense)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listOfExpenses.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowExpenseActivity::class.java)
            intent.putExtra("EXPANSE",expanses[i])
            startActivity(intent)
        }
    }

    fun refresh(){

        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<String>(0)

        expanses.clear()
        expanses = ArrayList<Expense>(db.expenseDAO().getAll())
        expanses.addAll(ArrayList<Expense>(db.constrantExpenseDAO().getAll()))

         for (i in expanses) {
            listItems.add(i.expenseName + " - " + i.shoppingDate)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        listOfExpenses.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    //jak naciśnięcie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{
        finish()
        return true
    }

    fun addExpense(view: View) {

        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
    }

}
