package com.example.monika.inzynierka

import com.example.monika.inzynierka.DataStructure.Product
import com.example.monika.inzynierka.DataStructure.Expense
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.monika.inzynierka.DataStructure.DatabaseRoom
import kotlinx.android.synthetic.main.activity_show_product.*

class ShowProductActivity : AppCompatActivity() {


    var writeProduct: Product= Product()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)

        writeProduct = intent.getSerializableExtra("product") as Product

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ProductName.isEnabled=false
        ProductPrize.isEnabled=false
        ProductGuarrantyDate.isEnabled=false


        //wypisywanie na danej pozycji tego tego

        if(writeProduct.getBitmapPhoto()!=null){

            ProductPhoto.setImageBitmap(writeProduct.getBitmapPhoto())
        }

        ProductPrize.setText(writeProduct.prise.toString())
        ProductName.setText(writeProduct.name)
        ProductGuarrantyDate.setText(writeProduct.guarrantyDate)



        //-------------------------------------------------------------------------------

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.edit_delete, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.editButton->{
                Toast.makeText(this,"EDIT CLICKED",Toast.LENGTH_LONG).show()
            }

            R.id.deleteButton->{
                DatabaseRoom.getAppDataBase()!!.productDAO().delete(writeProduct)
                finish()
                Toast.makeText(this,"DELETE CLICKED",Toast.LENGTH_LONG).show()
            }

            //jak naciśnie się na strzałkę u góry, to jest powrót
            android.R.id.home -> {
                finish()
            }

        }

        return true
    }

}
