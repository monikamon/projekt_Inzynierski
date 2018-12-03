package pl.polsl.project.activityClasses.fragmentsClasses

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list_expenses_all.*
import pl.polsl.project.R
import pl.polsl.project.activityClasses.ListExpensesTabbedActivity
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.listsViews.ListElementAdapter
import pl.polsl.project.listsViews.listClasses.ListElement
import java.lang.Math.exp
import java.lang.Math.floor
import java.text.SimpleDateFormat
import java.util.*

class AllElementsFragment : Fragment() {


    var db : DatabaseRoom = DatabaseRoom.getAppDataBase()!!
    var expanses:ArrayList<Expense> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        var rootView = inflater.inflate(R.layout.fragment_list_expenses_all, container, false)

        return rootView
    }

    fun refresh(){

        var sortType = floor(ListExpensesTabbedActivity.sortType/2.0).toInt()

        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<ListElement>(0)

        expanses.clear()
        expanses = ArrayList(db.expenseDAO().getAll())
        expanses.addAll(ArrayList<Expense>(db.constrantExpenseDAO().getAll()))

        for(expense in expanses){

            var sortText = expense.shoppingDate



            when(sortType){
                0 ->{
                    var category = DatabaseRoom.getAppDataBase()!!.categoryDAO().getCategory(expense.categoryId!!)
                    sortText = category.get(0).name
                }

                1 ->{
                    sortText = expense.price.toString()
                }

                2 ->{
                    sortText = expense.shoppingDate
                }
            }

            var founded: Boolean = false
            for(elementItems in listItems){

                if(elementItems.title == sortText){

                    founded = true
                    elementItems.expenseList.add(expense)
                    break
                }
            }

            if(founded == false){

                var elementInList: ListElement = ListElement()
                elementInList.title = sortText
                elementInList.expenseList.add(expense)
                listItems.add(elementInList)
            }

        }

        when(sortType) {
            0 -> {
                listItems.sortBy { listElement -> listElement.title }
            }

            1 -> {
                listItems.sortWith(object: Comparator<ListElement> {
                    override fun compare(p1: ListElement, p2: ListElement): Int = when {
                        p1.title.toDouble() > p2.title.toDouble() -> 1
                        p1.title.toDouble() == p2.title.toDouble() -> 0
                        else -> -1
                    }
                })

            }

            2 -> {
                listItems.sortWith(object: Comparator<ListElement> {
                    override fun compare(p1: ListElement, p2: ListElement): Int {
                        val df = SimpleDateFormat("dd/MM/yy")
                        df.isLenient = false
                        val date1: Date = df.parse(p1.title)
                        val date2: Date = df.parse(p2.title)
                        return date1.compareTo(date2)
                    }
                })
            }
        }

        if(ListExpensesTabbedActivity.sortType%2 == 1)
            listItems.reverse()

        val adapter =  ListElementAdapter(activity!!,layoutInflater,listItems)
        listOfExpenses.adapter = adapter

    }


    override fun onResume() {
        super.onResume()
        refresh()
    }


}