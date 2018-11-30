package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<String>(0)

        //TODO  change
        //var blabla=intent.getSerializableExtra("Dane")as Data
       // for (i in 0 until blabla.expenseList.size) {
        //    listItems.add("Wydatek: " +blabla.expenseList.get(i).shoppingDate)
        //}

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        listOfExpenses.adapter = adapter

        listOfExpenses.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowExpenseActivity::class.java)
            startActivity(intent)
        }


    }

    //jak naciśnięcie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun addExpense(view: View){

        val intent = Intent(this, ExpenseActivity::class.java)
        startActivity(intent)
    }

    fun addPhoto(view: View){

        val intent = Intent(this, ChoosePhotoAction::class.java)
        startActivity(intent)
    }

}
