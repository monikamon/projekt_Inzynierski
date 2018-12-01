package com.example.monika.inzynierka

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.monika.inzynierka.DataStructure.Category
import com.example.monika.inzynierka.DataStructure.tools.DatabaseRoom
import com.example.monika.inzynierka.Dialog.AddCategoryDialog
import kotlinx.android.synthetic.main.activity_category.*
import java.util.*

class KategoryActivity : AppCompatActivity() {

    var list:List<Category>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)



    }


    fun AddCategory(view: View){

        val dialog = AddCategoryDialog()
        dialog.show(supportFragmentManager, "dialog1")
    }

    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    override fun onResume(){
        super.onResume()
        refresh()
    }

    //ustawnienei na nowo zdjecia
    fun refresh(){

        list=DatabaseRoom.getAppDataBase()!!.categoryDAO().getAll()


        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<String>(0)


        for (element in list!!) {
            listItems.add(element.name)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        CategoryList.adapter = adapter


    }
}
