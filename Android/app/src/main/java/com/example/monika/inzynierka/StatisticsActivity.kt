package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.monika.inzynierka.R.id.CancelButton

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        //możliwe, że tu jeszcze czegoś brakuje, trzeba sprawdzić :)
    }


    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun backToMainScreen(view: View){

        finish()
    }

    fun addExpense(view: View){

        val intent = Intent (this, AddExpenseActivity::class.java)
        startActivity(intent)
    }


}
