package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
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
