package pl.polsl.project.dialogsFragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_sort.*
import pl.polsl.project.R
import pl.polsl.project.activityClasses.ListExpensesTabbedActivity

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "NAME_SHADOWING")
open class SortingDialog : DialogFragment(){

    //klasa która odpowiada za sortowanie wydatków
    //można sortować, po kategorii, cenie oraz dacie (wybór należy do użytkownika)
    var activity: ListExpensesTabbedActivity?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.activity_sort, container)

    }

    //wybranie co ma się stać po otwarciu dialogu na podstawie wybranego guzika
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val listElements = ArrayList<String>()

        listElements.add(getString(R.string.sort_option_1))
        listElements.add(getString(R.string.sort_option_2))
        listElements.add(getString(R.string.sort_option_3))
        listElements.add(getString(R.string.sort_option_4))
        listElements.add(getString(R.string.sort_option_5))
        listElements.add(getString(R.string.sort_option_6))

        val adapter = ArrayAdapter(activity, android.R.layout.simple_spinner_item, listElements)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sortType.adapter = adapter
        sortType.setSelection(ListExpensesTabbedActivity.sortType)



        apply.setOnClickListener{
            view ->
            ListExpensesTabbedActivity.sortType = sortType.selectedItemPosition
            activity!!.refresh()
            dismiss()

        }

    }

}