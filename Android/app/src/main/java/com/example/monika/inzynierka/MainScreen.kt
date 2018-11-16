package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.View
import android.widget.EditText
import com.example.monika.inzynierka.R.layout.activity_statistics

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

        val intent=Intent(this, ExpenseActivity::class.java)
        startActivity(intent)
    }
}

