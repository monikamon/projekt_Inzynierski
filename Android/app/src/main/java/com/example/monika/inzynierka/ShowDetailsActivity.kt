package com.example.monika.inzynierka

import DataStructure.Expense
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_show_details.*
import kotlinx.android.synthetic.main.activity_show_expense.view.*

class ShowDetailsActivity : AppCompatActivity() {


    var writeExpense: Expense= Expense()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_details)


        //TODO tylko dla testu
        writeExpense.prise=13.99
        writeExpense.shoppingDate="06.12.2018"
        //---------------------------------------------------------------------------


        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ShoppingDate.isEnabled=false
        ShoppingPrize.isEnabled=false
        ConstrantExpense.isEnabled=false

        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<String>(25)

        for (i in 0 until 25) {
            listItems.add("Wydatek: " +i)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        ListOfProducts.adapter = adapter

        ListOfProducts.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowExpenseActivity::class.java)
            startActivity(intent)
        }


        //TODO wypisywanie na danej pozycji tego tego
        ShoppingPrize.setText(writeExpense.prise.toString())
        ShoppingDate.setText(writeExpense.shoppingDate.toString())
        //-------------------------------------------------------------------------------

    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun addProduct(view: View){

        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
    }



}
