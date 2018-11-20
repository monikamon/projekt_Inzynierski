package com.example.monika.inzynierka

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_show_expense.*

class ShowExpenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_expense)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //CancelButton.setOnClickListener{backToDetails()}

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ProductName.isEnabled=false
        ProductPrize.isEnabled=false
        ProductGuarrantyDate.isEnabled=false
    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

}
