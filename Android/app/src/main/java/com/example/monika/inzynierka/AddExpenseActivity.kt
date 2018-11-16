package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)


    }

    fun addPhoto(view: View){

        val intent = Intent(this, ChoosePhotoAction::class.java)
        startActivity(intent)
    }

    fun backToDetails(view: View){

        val intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent)
    }
}
