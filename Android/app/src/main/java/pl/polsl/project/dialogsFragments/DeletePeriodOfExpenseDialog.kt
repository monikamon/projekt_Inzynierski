package pl.polsl.project.dialogsFragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.R
import kotlinx.android.synthetic.main.activity_delete_period.*
import pl.polsl.project.activityClasses.ListExpensesTabbedActivity
import pl.polsl.project.databaseStructure.dataStructure.Expense
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING")
class DeletePeriodOfExpenseDialog : DialogFragment(){

    var db : DatabaseRoom = DatabaseRoom.getAppDataBase()!!
    var activity: ListExpensesTabbedActivity?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_delete_period, container)

    }

    //wybranie co ma się stać po otwarciu dialogu na podstawie wybranego guzika
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //zamknięcie dialogu
        cancleButtonDel.setOnClickListener{
            view -> dismiss()
        }

        acceptButtonDel.setOnClickListener{
            view ->

            var date_from = isValidDate(fromDate.text.toString())
            var date_to = isValidDate(toDate.text.toString())

            if(date_from != null && date_to != null){

                if(date_from.compareTo(date_to) != 1){

                    var expanses = ArrayList(db.expenseDAO().getAll())
                    expanses.addAll(ArrayList<Expense>(db.constrantExpenseDAO().getAll()))

                    for(element in expanses){

                        var date = isValidDate(element.shoppingDate)
                        if(date!!.compareTo(date_from) != -1 && date.compareTo(date_to) != 1){

                            DatabaseRoom.deleteExpenseWithProducts(element)
                        }
                    }
                    dismiss()
                }else{

                    Toast.makeText(context, getString(R.string.wrongDate), Toast.LENGTH_SHORT).show()
                }

            }else{

                Toast.makeText(context, getString(R.string.invalid_date), Toast.LENGTH_SHORT).show()
            }

            activity!!.refresh()
        }



    }

    private fun isValidDate(text:String): Date? {

        if(!text.matches("^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$".toRegex())){
            return null
        }

        val df = SimpleDateFormat("dd/MM/yy")
        df.isLenient = false
        try {
            return df.parse(text)
        } catch (e: Exception){
            return null
        }

    }

}