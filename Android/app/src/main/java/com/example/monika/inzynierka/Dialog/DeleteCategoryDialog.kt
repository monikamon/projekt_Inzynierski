package com.example.monika.inzynierka.Dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.monika.inzynierka.CategoryActivity
import com.example.monika.inzynierka.DataStructure.Category
import com.example.monika.inzynierka.DataStructure.tools.DatabaseRoom
import com.example.monika.inzynierka.R
import kotlinx.android.synthetic.main.activity_delete_category.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING")
class DeleteCategoryDialog : DialogFragment(){

    var categoryToDelete: Category?=null
    var activity: CategoryActivity?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_delete_category, container)

    }

    //wybranie co ma się stać po otwarciu dialogu na podstawie wybranego guzika
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryTitleName.setText(categoryToDelete!!.name)

        //zamknięcie dialogu
        cancelButton.setOnClickListener{
            view -> dismiss()
        }

        acceptButton.setOnClickListener{
            view ->
           if( DatabaseRoom.deleteCategory(categoryToDelete!!)){
               activity!!.refresh()
               dismiss()
           }else{

               Toast.makeText(context,getString(R.string.cannot_delete_category),Toast.LENGTH_LONG).show()
               dismiss()
           }


        }

    }

}