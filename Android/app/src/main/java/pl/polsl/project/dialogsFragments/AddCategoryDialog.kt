package pl.polsl.project.dialogsFragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import pl.polsl.project.activityClasses.CategoryActivity
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.R
import kotlinx.android.synthetic.main.activity_add_category.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING")
open class AddCategoryDialog : DialogFragment(){

    //klasa odpowiadająca za dialog dla dodawania kategorii
    var activity: CategoryActivity?=null

    //stworzenie okna przy uruchomieniu ekranu
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
                Toast.makeText(context,getString(R.string.allert_name), Toast.LENGTH_LONG).show()
            }else{
                val newCategory = Category(nameOfCategory.text.toString())

                if(DatabaseRoom.addCategory(newCategory)) {
                    activity!!.refresh()
                    dismiss()
                }else{
                    Toast.makeText(context,getString(R.string.allert_category), Toast.LENGTH_LONG).show()
                }
            }


        }

    }

}