package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_expense.*

class ExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)


        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<String>(25)

        for (i in 0 until 25) {
            listItems.add("Wydatek: " +i)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        listOfExpenses.adapter = adapter
    }

    fun addExpense(view: View){

        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }

    fun addPhoto(view: View){

        val intent = Intent(this, ChoosePhotoAction::class.java)
        startActivity(intent)
    }
}
