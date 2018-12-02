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
import com.example.monika.inzynierka.R
import kotlinx.android.synthetic.main.activity_add_category.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING")
class EditCategoryDialog : AddCategoryDialog(){

    var category: Category? = null

    //wybranie co ma się stać po otwarciu dialogu na podstawie wybranego guzika
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        acceptButton.setOnClickListener{
            view ->

            if(nameOfCategory.text.isEmpty()){
                Toast.makeText(getContext(),getString(R.string.allert_name), Toast.LENGTH_LONG).show()
            }else{
                var oldName = category!!.name
                category!!.name = nameOfCategory.text.toString()

                if(DatabaseRoom.addCategory(category!!)) {
                    activity!!.refresh()
                    dismiss()
                }else{
                    category!!.name = oldName
                    Toast.makeText(getContext(),getString(R.string.allert_category), Toast.LENGTH_LONG).show()
                }
            }


        }

    }

}