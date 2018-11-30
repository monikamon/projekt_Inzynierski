package com.example.monika.inzynierka

import com.example.monika.inzynierka.DataStructure.ConstrantExpense
import com.example.monika.inzynierka.DataStructure.Expense
import com.example.monika.inzynierka.DataStructure.Product
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.monika.inzynierka.DataStructure.DatabaseRoom
import kotlinx.android.synthetic.main.activity_show_expense.*

class ShowExpenseActivity : AppCompatActivity() {


    var db : DatabaseRoom = DatabaseRoom.getAppDataBase()!!
    var showExpense: Expense= Expense()
    var list:ArrayList<Product> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_expense)

        showExpense=intent.getSerializableExtra("EXPANSE")as Expense

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ShoppingDate.isEnabled=false
        ShoppingPrize.isEnabled=false
        ConstrantExpense.isEnabled=false

        ListOfProducts.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowProductActivity::class.java)
            intent.putExtra("product", list.get(i))
            startActivity(intent)
        }
    }

    fun refresh(){

        if(showExpense.getBitmapPhoto()!=null){

            ReceiptPhoto.setImageBitmap(showExpense.getBitmapPhoto())
        }

        //sprawdzenie, czy coś jest wybranej klasy
        if(showExpense is ConstrantExpense){
            //ustawienie wartości checkboxa na true!
            ConstrantExpense.isSelected=true
        }

        //zainicjowanie listy mniej dupiatymi elementami
        val listItems = ArrayList<String>()
        list.clear()
        list.addAll(db.productDAO().getProductFromExpense(showExpense.id!!))

        for (element in list) {
            listItems.add(element.name)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        ListOfProducts.adapter = adapter

        //wypisywanie na danej pozycji tego tego
        ShoppingPrize.setText(showExpense.price.toString())
        ShoppingDate.setText(showExpense.shoppingDate)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun addProduct(view: View){

        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra("ExpanseName", showExpense)
        startActivity(intent)
    }



}
