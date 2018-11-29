package com.example.monika.inzynierka

import DataStructure.Product
import DataStructure.Expense
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_show_product.*

class ShowProductActivity : AppCompatActivity() {


    var writeProduct: Product= Product()

    //pobranie paczki danych i rzutownaie na klasę Expense
    var expense:Expense = Expense()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)

        expense=intent.getSerializableExtra("ExpenseData") as Expense

        var productId=intent.getIntExtra("ProductId", -1)
        writeProduct=expense.listOfProducts.get(productId)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //CancelButton.setOnClickListener{backToDetails()}

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ProductName.isEnabled=false
        ProductPrize.isEnabled=false
        ProductGuarrantyDate.isEnabled=false


        //TODO wypisywanie na danej pozycji tego tego
        ProductPrize.setText(writeProduct.prise.toString())
        ProductName.setText(writeProduct.name)
        ProductGuarrantyDate.setText(writeProduct.guarrantyDate)



        //-------------------------------------------------------------------------------

    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

}
