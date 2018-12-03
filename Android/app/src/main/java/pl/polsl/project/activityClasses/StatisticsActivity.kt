package pl.polsl.project.activityClasses

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_statistics.*
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.listsViews.listClasses.ListElement
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNUSED_PARAMETER")
class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listElements = ArrayList<String>()

        listElements.add(getString(R.string.chart_option1))
        listElements.add(getString(R.string.chart_option2))
        listElements.add(getString(R.string.chart_option3))
        listElements.add(getString(R.string.chart_option4))

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listElements)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerType.adapter = adapter
        spinnerType.setSelection(3)

        spinnerType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                dateText.visibility = View.VISIBLE
                if(pos == 0){

                    dateText.hint = getString(R.string.hint_option1)
                } else if(pos == 1){

                    dateText.hint = getString(R.string.hint_option2)
                } else if(pos ==2){
                    dateText.hint = getString(R.string.hint_option3)
                } else{

                    dateText.hint = getString(R.string.hint_option4)
                    dateText.visibility = View.GONE
                }
            }


        }

        chartType4()
        barChart("")

    }


    fun chartType1(dzien:String){

        var labels = ArrayList<String>()
        labels.add(dzien)



        var expanses = java.util.ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAll())
        expanses.addAll(java.util.ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAll()))

        val entries = ArrayList<BarEntry>()


        var sum: Double = 0.0

        for (data in expanses) {
            if(data.shoppingDate == dzien){

                sum += data.price
            }

        }

        entries.add(BarEntry(0.0f, sum.toFloat()))




        chartExpense.xAxis.setLabelCount(labels.size)
        chartExpense.xAxis.labelRotationAngle = 45.0f
        chartExpense.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        var dataSet = BarDataSet (entries, "")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()


        val data = BarData(dataSet)

        data.setValueTextSize(10f)
        data.barWidth = 0.9f


        chartExpense.data = data

        chartExpense.setPinchZoom(false)
        chartExpense.setScaleEnabled(false)

        chartExpense.axisLeft.axisMinimum = 0.0f
        chartExpense.axisRight.isEnabled = false

        chartExpense.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartExpense.xAxis.setDrawGridLines(false)
        chartExpense.legend.isEnabled = false
        chartExpense.description.isEnabled = false

        chartExpense.animateY(2000)

        chartExpense.invalidate() // refresh


    }

    fun chartType2(miesiac:String){


    }

    fun chartType3(rok:String){


    }

    fun chartType4(){

        var labels = ArrayList<String>()




        var expanses = java.util.ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAll())
        expanses.addAll(java.util.ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAll()))

        expanses.sortWith(object: Comparator<Expense> {
            override fun compare(p1: Expense, p2: Expense): Int {
                val df = SimpleDateFormat("dd/MM/yy")
                df.isLenient = false
                val date1: Date = df.parse(p1.shoppingDate)
                val date2: Date = df.parse(p2.shoppingDate)
                return date1.compareTo(date2)
            }
        })

        val entries = ArrayList<BarEntry>()

        if(expanses.size > 0) {
            var dateLastYear = expanses.get(0).shoppingDate.substring(6)
            var dateFirstYear = expanses.get(expanses.size - 1).shoppingDate.substring(6)




//            var sum: Double = 0.0
//
//            for (data in expanses) {
//                if(data.shoppingDate == dzien){
//
//                    sum += data.price
//                }
//
//            }

//            entries.add(BarEntry(0.0f, sum.toFloat()))
        }








        chartExpense.xAxis.setLabelCount(labels.size)
        chartExpense.xAxis.labelRotationAngle = 45.0f
        chartExpense.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        var dataSet = BarDataSet (entries, "")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()


        val data = BarData(dataSet)

        data.setValueTextSize(10f)
        data.barWidth = 0.9f


        chartExpense.data = data

        chartExpense.setPinchZoom(false)
        chartExpense.setScaleEnabled(false)

        chartExpense.axisLeft.axisMinimum = 0.0f
        chartExpense.axisRight.isEnabled = false

        chartExpense.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartExpense.xAxis.setDrawGridLines(false)
        chartExpense.legend.isEnabled = false
        chartExpense.description.isEnabled = false

        chartExpense.animateY(2000)

        chartExpense.invalidate() // refresh

    }

    fun barChart(date:String){

        //region Wykres kategorie
        var labels = ArrayList<String>()

        var category = DatabaseRoom.getAppDataBase()!!.categoryDAO().getAll()
        val entries = ArrayList<BarEntry>()

        var index = 0
        for (data in category) {

            var sum: Double = 0.0
            var expanses = java.util.ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getExpanseFromCategory(data.id!!))
            expanses.addAll(java.util.ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getExpanseFromCategory(data.id!!)))

            for(elementExpense in expanses){

                if(elementExpense.shoppingDate.endsWith(date) || date=="") {

                    sum += elementExpense.price
                }
            }

            entries.add(BarEntry(index.toFloat(), sum.toFloat()))
            labels.add(data.name)
            index+=1
        }

        chartCategory.xAxis.setLabelCount(category.size)
        chartCategory.xAxis.labelRotationAngle = 45.0f
        chartCategory.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        var dataSet = BarDataSet (entries, "")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()


        val data = BarData(dataSet)

        data.setValueTextSize(10f)
        data.barWidth = 0.9f


        chartCategory.data = data

        chartCategory.setPinchZoom(false)
        chartCategory.setScaleEnabled(false)

        chartCategory.axisLeft.axisMinimum = 0.0f
        chartCategory.axisRight.isEnabled = false

        chartCategory.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chartCategory.xAxis.setDrawGridLines(false)
        chartCategory.legend.isEnabled = false
        chartCategory.description.isEnabled = false

        chartCategory.animateY(2000)

        chartCategory.invalidate() // refresh

        //endregion
    }


    fun onClickOk(view: View){
        if(spinnerType.selectedItemPosition == 0){
            if(!isValidDate(dateText.text.toString(),"dd/MM/yy","^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$")){
                Toast.makeText(this, getString(R.string.invalid_date_statistics), Toast.LENGTH_SHORT).show()
                return
            }else{
                chartType1(dateText.text.toString())
                barChart(dateText.text.toString())
            }

        } else if(spinnerType.selectedItemPosition == 1){

            if(!isValidDate(dateText.text.toString(),"MM/yy","^[0-9]{2}[/][0-9]{4}$")) {
                Toast.makeText(this, getString(R.string.invalid_date_statistics), Toast.LENGTH_SHORT).show()
                return
            }else{
                chartType2(dateText.text.toString())
                barChart(dateText.text.toString())
            }
        } else if(spinnerType.selectedItemPosition == 2){

            if(!isValidDate(dateText.text.toString(),"yy","^[0-9]{4}$")) {
                Toast.makeText(this, getString(R.string.invalid_date_statistics), Toast.LENGTH_SHORT).show()
                return
            }else{
                chartType3(dateText.text.toString())
                barChart(dateText.text.toString())
            }
        } else{
            chartType4()
            barChart("")
        }

    }


    fun isValidDate(text:String,dateFormat:String,regex:String): Boolean {

        if(!text.matches(regex.toRegex())){
            return false
        }

        val df = SimpleDateFormat(dateFormat)
        df.isLenient = false
        try {
            df.parse(text)
            return true
        } catch (e: Exception){
            return false
        }

    }


//naciśnięcie strzałki wywołuje zamknięcie okna i powrót do okna poprzednio otwartego
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun backToMainScreen(view: View){

        finish()
    }

}
