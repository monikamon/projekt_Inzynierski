package com.example.monika.inzynierka

import Dialog.CategoryDialog
import Dialog.PhotoDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


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

    fun addPhotoReceipt(view: View){


        val dialog = PhotoDialog()
        dialog.pm=packageManager
        dialog.show(supportFragmentManager, "dialog1")
    }

    fun chooseCategory(view: View){

        val dialog = CategoryDialog()
        dialog.show(supportFragmentManager, "dialog2")
    }

}
