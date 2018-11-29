package com.example.monika.inzynierka

import DataStructure.ConstrantExpense
import DataStructure.Expense
import DataStructure.Product
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_show_expense.*

class ShowExpenseActivity : AppCompatActivity() {


    var showExpense: Expense= Expense()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_expense)


        //TODO tylko dla testu
        showExpense.price=13.99
        showExpense.shoppingDate="06.12.2018"


        for (i in 0 until 8) {
            var product=Product()
            product.name=(i*647/3).toString()
            showExpense.listOfProducts.add(product)
        }
        //---------------------------------------------------------------------------


        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ShoppingDate.isEnabled=false
        ShoppingPrize.isEnabled=false
        ConstrantExpense.isEnabled=false

        //sprawdzenie, czy coś jest wybranej klasy
        if(showExpense is ConstrantExpense){
            //ustawienie wartości checkboxa na true!
            ConstrantExpense.isSelected=true
        }

        //zainicjowanie listy mniej dupiatymi elementami
        val listItems = ArrayList<String>()

        for (element in showExpense.listOfProducts) {
            listItems.add(element.name)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        ListOfProducts.adapter = adapter

        ListOfProducts.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowProductActivity::class.java)
            intent.putExtra("ExpenseData", showExpense)
            intent.putExtra("ProductId", i)
            startActivity(intent)
        }


        //TODO wypisywanie na danej pozycji tego tego
        ShoppingPrize.setText(showExpense.price.toString())
        ShoppingDate.setText(showExpense.shoppingDate.toString())
        //-------------------------------------------------------------------------------

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



}
