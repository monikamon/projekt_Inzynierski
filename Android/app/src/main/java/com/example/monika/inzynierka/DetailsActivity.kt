package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }

    fun addProduct(view: View){

        val intent = Intent(this, AddExpenseActivity::class.java)
        startActivity(intent)
    }

    fun addPhotoReceipt(view: View){

        val intent = Intent(this, ChoosePhotoAction::class.java)
        startActivity(intent)
    }

    fun chooseCategory(view: View){

        val intent = Intent(this, KategoryActivity::class.java)
        startActivity(intent)
    }

    fun backToExpense(view: View){

        val intent = Intent(this, ExpenseActivity::class.java)
        startActivity(intent)
    }
}
