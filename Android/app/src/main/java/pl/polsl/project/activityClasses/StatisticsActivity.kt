package pl.polsl.project.activityClasses

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_statistics.*
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import java.text.SimpleDateFormat
import java.util.*

@Suppress("UNUSED_PARAMETER", "ObjectLiteralToLambda", "LiftReturnOrAssignment", "ConvertToStringTemplate")
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
                when (pos) {
                    0 -> dateText.hint = getString(R.string.hint_option1)
                    1 -> dateText.hint = getString(R.string.hint_option2)
                    2 -> dateText.hint = getString(R.string.hint_option3)
                    else -> {

                        dateText.hint = getString(R.string.hint_option4)
                        dateText.visibility = View.GONE
                    }
                }
            }


        }


        chartExpense.setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
            override fun onNothingSelected() {

            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Toast.makeText(applicationContext,getString(R.string.day)+ ": " + e!!.x.toInt().toString()+" " + getString(R.string.spend) + ": " + e.y.toString(),Toast.LENGTH_SHORT).show()

            }

        })


        chartCategory.setOnChartValueSelectedListener(object: OnChartValueSelectedListener {
            override fun onNothingSelected() {

            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Toast.makeText(applicationContext,getString(R.string.day)+ ": " +  e!!.x.toInt().toString() +" " + getString(R.string.spend) + ": " + e.y.toString(),Toast.LENGTH_SHORT).show()

            }

        })

        chartType4()
        barChart("")

    }


    private fun chartType1(dzien:String){

        val labels = ArrayList<String>()

        labels.add("")
        labels.add(dzien)



        val expanses = ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAllWithoutPhoto())
        expanses.addAll(ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAllWithoutPhoto()))

        val entries = ArrayList<BarEntry>()


        var sum = 0.0

        for (data in expanses) {
            if(data.shoppingDate == dzien){

                sum += data.price
            }

        }

        entries.add(BarEntry(1.0f, sum.toFloat()))




        chartExpense.xAxis.labelCount = labels.size
        chartExpense.xAxis.labelRotationAngle = 45.0f
        chartExpense.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        val dataSet = BarDataSet (entries, "")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()


        val data = BarData(dataSet)

        data.setValueTextSize(10f)
        data.barWidth = 0.3f


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

    @SuppressLint("SimpleDateFormat")
    private fun chartType2(miesiac:String){

        val labels = ArrayList<String>()
        labels.add("")

        val expanses = ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAllWithoutPhoto())
        expanses.addAll(ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAllWithoutPhoto()))

        val entries = ArrayList<BarEntry>()

        if(expanses.size > 0) {

            val df = SimpleDateFormat("dd/MM/yy")
            df.isLenient = false
            val date1: Date = df.parse("01/"+miesiac)

            val calendar = Calendar.getInstance()
            calendar.time = date1

            calendar.add(Calendar.MONTH,1)
            calendar.add(Calendar.DAY_OF_YEAR,-1)

            val firstday = 1
            val lastday= calendar.get(Calendar.DAY_OF_MONTH)

            for(i in firstday until lastday+1){

                var sum = 0.0

                for (data in expanses) {

                    var  str: String
                    if(i < 10)
                        str = "0" + (i).toString() + "/" + miesiac
                    else
                        str = (i).toString()+"/"+miesiac


                    if (data.shoppingDate.endsWith(str)) {
                        sum += data.price
                    }

                }

                entries.add(BarEntry(i.toFloat(), sum.toFloat()))
                labels.add(i.toString())

            }

        }



        chartExpense.xAxis.labelCount = labels.size
        chartExpense.xAxis.labelRotationAngle = 90.0f
        chartExpense.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        val dataSet = BarDataSet (entries, "")
        dataSet.colors = ColorTemplate.COLORFUL_COLORS.asList()


        val data = BarData(dataSet)

        data.setValueTextSize(0.0f)

        data.barWidth = 0.8f


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

    private fun chartType3(rok:String){

        val labels = ArrayList<String>()
        labels.add(getString(R.string.month1))
        labels.add(getString(R.string.month2))
        labels.add(getString(R.string.month3))
        labels.add(getString(R.string.month4))
        labels.add(getString(R.string.month5))
        labels.add(getString(R.string.month6))
        labels.add(getString(R.string.month7))
        labels.add(getString(R.string.month8))
        labels.add(getString(R.string.month9))
        labels.add(getString(R.string.month10))
        labels.add(getString(R.string.month11))
        labels.add(getString(R.string.month12))

        val expanses = ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAllWithoutPhoto())
        expanses.addAll(ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAllWithoutPhoto()))

        val entries = ArrayList<BarEntry>()

        if(expanses.size > 0) {

            var index = 0
            for(i in 0 until 12){

                var sum = 0.0

                for (data in expanses) {

                    var  str: String
                    if(i+1 < 10)
                        str = "0" + (i + 1).toString() + "/" + rok
                    else
                        str = (i+1).toString()+"/"+rok


                    if (data.shoppingDate.endsWith(str)) {
                        sum += data.price
                    }
                }

                entries.add(BarEntry(index.toFloat(), sum.toFloat()))
                index+=1

            }

        }



        chartExpense.xAxis.labelCount = labels.size
        chartExpense.xAxis.labelRotationAngle = 45.0f
        chartExpense.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        val dataSet = BarDataSet (entries, "")
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

    private fun chartType4(){

        val labels = ArrayList<String>()




        val expanses = ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getAllWithoutPhoto())
        expanses.addAll(ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getAllWithoutPhoto()))

        expanses.sortWith(object: Comparator<Expense> {
            @SuppressLint("SimpleDateFormat")
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
            val dateFirstYear = expanses.get(0).shoppingDate.substring(6).toInt()
            val dateLastYear = expanses.get(expanses.size - 1).shoppingDate.substring(6).toInt()


            var index = 0
            for(i in dateFirstYear until dateLastYear+1){

                var sum = 0.0

                for (data in expanses) {
                    if(data.shoppingDate.endsWith(i.toString())){

                        sum += data.price
                    }

                }

                entries.add(BarEntry(index.toFloat(), sum.toFloat()))
                labels.add(i.toString())
                index+=1
            }

        }



        chartExpense.xAxis.labelCount = labels.size
        chartExpense.xAxis.labelRotationAngle = 45.0f
        chartExpense.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val index = Math.round(value)

                return if (index < 0 || index >= labels.size || index != value.toInt()) "" else labels[index]

            }

        }


        val dataSet = BarDataSet (entries, "")
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

    private fun barChart(date:String){

        //region Wykres kategorie
        val labels = ArrayList<String>()

        val category = DatabaseRoom.getAppDataBase()!!.categoryDAO().getAll()
        val entries = ArrayList<BarEntry>()

        var index = 0
        for (data in category) {

            var sum = 0.0
            val expanses = ArrayList(DatabaseRoom.getAppDataBase()!!.expenseDAO().getExpanseFromCategory(data.id!!))
            expanses.addAll(ArrayList<Expense>(DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getExpanseFromCategory(data.id!!)))

            for(elementExpense in expanses){

                if(elementExpense.shoppingDate.endsWith(date) || date=="") {

                    sum += elementExpense.price
                }
            }

            entries.add(BarEntry(index.toFloat(), sum.toFloat()))
            labels.add(data.name)
            index+=1
        }

        chartCategory.xAxis.labelCount = category.size
        chartCategory.xAxis.labelRotationAngle = 45.0f
        chartCategory.xAxis.valueFormatter = object: IndexAxisValueFormatter(){

            override fun getFormattedValue( value: Float,  axis: AxisBase):String {
                val indexFormat = Math.round(value)

                return if (indexFormat < 0 || indexFormat >= labels.size || indexFormat != value.toInt()) "" else labels[indexFormat]

            }

        }


        val dataSet = BarDataSet (entries, "")
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


    @SuppressLint("SimpleDateFormat")
    private fun isValidDate(text:String, dateFormat:String, regex:String): Boolean {

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

}
