package com.example.monika.inzynierka.Dialog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.monika.inzynierka.DataStructure.Category
import com.example.monika.inzynierka.DataStructure.tools.DatabaseRoom
import com.example.monika.inzynierka.R
import kotlinx.android.synthetic.main.activity_add_category.*

class AddCategoryDialog : DialogFragment(){


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_add_category, container)

    }

    //wybranie co ma się stać po otwarciu dialogu na podstawie wybranego guzika
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //zamknięcie dialogu
        cancelButton.setOnClickListener{
            view -> dismiss()
        }

        acceptButton.setOnClickListener{
            view ->

            if(nameOfCategory.text.isEmpty()){
                Toast.makeText(getContext(),getString(R.string.allert_name), Toast.LENGTH_LONG).show()
            }else{
                var newCategory: Category = Category(nameOfCategory.text.toString())
                DatabaseRoom.getAppDataBase()!!.categoryDAO().insert(newCategory)
                dismiss()
            }


        }

    }

}