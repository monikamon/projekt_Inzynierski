package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
    }

    fun backToMainScreen(view: View){

        val intent= Intent(this, MainScreen::class.java)
        startActivity(intent)

    }

    fun addExpense(view: View){

        val intent = Intent (this, AddExpenseActivity::class.java)
        startActivity(intent)
    }


}
