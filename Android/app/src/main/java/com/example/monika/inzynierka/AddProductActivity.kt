package com.example.monika.inzynierka

import android.graphics.Bitmap
import com.example.monika.inzynierka.Dialog.PhotoDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.monika.inzynierka.DataStructure.DatabaseRoom
import com.example.monika.inzynierka.DataStructure.Expense
import com.example.monika.inzynierka.DataStructure.Product
import kotlinx.android.synthetic.main.activity_add_product.*


class AddProductActivity : AppCompatActivity() {

    var expense: Expense? = null
    var photo: Bitmap? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        expense=intent.getSerializableExtra("ExpanseName") as Expense

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

    fun refresh(){


    }

    override fun onResume(){
        super.onResume()
        refresh()
    }

    fun AcceptProductButton(view: View){

        if(ProductName.text.isEmpty()){

            Toast.makeText(this, "Nie podano nazwy", Toast.LENGTH_SHORT).show()
            return
        }

        var product = Product()
        product.name = ProductName.text.toString()
        product.prise = ProductPrize.text.toString().toDouble()
        product.guarrantyDate = GuarrantyDate.text.toString()
        product.setBitmapPhoto(photo)
        product.expenseId = expense!!.id

        DatabaseRoom.getAppDataBase()!!.productDAO().insert(product)
        
        finish()
    }

}
