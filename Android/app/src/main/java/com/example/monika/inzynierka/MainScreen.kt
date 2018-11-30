package com.example.monika.inzynierka

import com.example.monika.inzynierka.DataStructure.Expense
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

    }


    fun showStatistics(view: View){

        val intent=Intent(this, StatisticsActivity::class.java)
        startActivity(intent)

    }

    fun showExpense(view: View){

        val intent=Intent(this, ProductActivity::class.java)
        startActivity(intent)
    }
}

