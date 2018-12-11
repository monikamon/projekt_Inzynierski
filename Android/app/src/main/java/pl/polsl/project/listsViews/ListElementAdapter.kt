package pl.polsl.project.listsViews

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import pl.polsl.project.R
import pl.polsl.project.activityClasses.ShowExpenseActivity
import pl.polsl.project.listsViews.listClasses.ListElement

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "ConvertSecondaryConstructorToPrimary")
class ListElementAdapter : BaseAdapter{

    private var elementList : ArrayList<ListElement> = arrayListOf()
    private var inflater: LayoutInflater? = null
    private var activity: Activity? = null

    constructor(activity: FragmentActivity, inflater : LayoutInflater, elementList: ArrayList<ListElement>) : super() {
        this.activity = activity
        this.inflater = inflater
        this.elementList = elementList
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        val rowView = inflater!!.inflate(R.layout.list_layout_element, parent, false)

        (rowView.findViewById(R.id.titleView) as TextView).text = elementList.get(position).title

        val listview = (rowView.findViewById(R.id.listOfList) as ListView)
        val listItems = ArrayList<String>(0)


        for (element in elementList.get(position).expenseList) {
            listItems.add(element.expenseName)
        }

        val adapter =  ArrayAdapter<String>( activity!!, android.R.layout.simple_list_item_1,listItems)
        listview.adapter = adapter


        var totalHeight = 0
        for (i in 0 until adapter.count) {
            val listItem = adapter.getView(i, null, listview)
            listItem.measure(0, 0)
            totalHeight += listItem.measuredHeight
        }

        val params = listview.layoutParams
        params.height = totalHeight + listview.dividerHeight * (adapter.count - 1)
        listview.layoutParams = params
        listview.requestLayout()


        listview.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(activity!!, ShowExpenseActivity::class.java)
            intent.putExtra("EXPANSE",elementList.get(position).expenseList[i])
            activity!!.startActivity(intent)
        }

        return rowView
    }

    override fun getItem(p0: Int): Any {
        return elementList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return elementList.size
    }


}