package com.example.monika.inzynierka

import com.example.monika.inzynierka.DataStructure.ConstrantExpense
import com.example.monika.inzynierka.DataStructure.Expense
import com.example.monika.inzynierka.DataStructure.Product
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.monika.inzynierka.DataStructure.tools.DatabaseRoom
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
        checkConstrantExpense.isEnabled=false
        categoryName.isEnabled=false

        ListOfProducts.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowProductActivity::class.java)
            intent.putExtra("product", list.get(i))
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.edit_delete, menu)

        return true
    }


    fun refresh(){

        supportActionBar!!.title =showExpense.expenseName

        if(showExpense.getBitmapPhoto()!=null){

            ReceiptPhoto.setImageBitmap(showExpense.getBitmapPhoto())
        }

        if(showExpense.categoryId==null){

            categoryName.setText(getString(R.string.no_category))
        }else{

            var category = DatabaseRoom.getAppDataBase()!!.categoryDAO().getCategory(showExpense.categoryId!!)
            categoryName.setText(category.get(0).name)
        }

        //sprawdzenie, czy coś jest wybranej klasy
        if(showExpense is ConstrantExpense){
            //ustawienie wartości checkboxa na true!
            checkConstrantExpense.isSelected=true
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

    fun addProduct(view: View){

        val intent = Intent(this, AddProductActivity::class.java)
        intent.putExtra("ExpanseName", showExpense)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.editButton->{
                Toast.makeText(this,"EDIT CLICKED", Toast.LENGTH_LONG).show()
            }

            R.id.deleteButton->{
                DatabaseRoom.deleteExpenseWithProducts(showExpense)
                finish()
                Toast.makeText(this,"DELETE CLICKED", Toast.LENGTH_LONG).show()
            }

            //jak naciśnie się na strzałkę u góry, to jest powrót
            android.R.id.home -> {
                finish()
            }

        }

        return true
    }

}
