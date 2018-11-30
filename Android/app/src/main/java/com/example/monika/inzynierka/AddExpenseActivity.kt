package com.example.monika.inzynierka

import com.example.monika.inzynierka.Dialog.PhotoDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)




    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun addPhoto(view: View){

        val dialog = PhotoDialog()
        dialog.pm=packageManager
        dialog.show(supportFragmentManager, "dialog1")

    }



}
