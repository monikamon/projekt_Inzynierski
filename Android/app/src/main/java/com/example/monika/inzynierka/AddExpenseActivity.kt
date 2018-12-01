package com.example.monika.inzynierka

import com.example.monika.inzynierka.Dialog.PhotoDialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.monika.inzynierka.DataStructure.ConstrantExpense
import com.example.monika.inzynierka.DataStructure.ConstrantExpenseTime
import com.example.monika.inzynierka.DataStructure.Expense
import com.example.monika.inzynierka.DataStructure.tools.DatabaseRoom
import com.example.monika.inzynierka.DataStructure.tools.returnPhotoInterface
import kotlinx.android.synthetic.main.activity_add_expense.*

class AddExpenseActivity : AppCompatActivity(), returnPhotoInterface {

    var photo: Bitmap?=null

    override fun returnPhoto(pictureBitmap: Bitmap) {

        photo=pictureBitmap
        refresh()
    }

//    val spinnerList = ArrayList<String>()



//    spinnerList.add(getString(R.string.name))
//
//    if(element != null) {
//        spinnerTemplate.add(getString(R.string.rating_label))
//
//        for (i in element!!.list)
//            spinnerTemplate.add(i.title)
//
//    }


//
//    val dataAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerTemplate)
//    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//    sortBy.adapter = dataAdapter
//    sortBy.setSelection(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        howOftenToPay.visibility=View.GONE
        constrantExpenseSpinner.visibility= View.GONE

        val listElements = ArrayList<String>()

        listElements.add(getString(R.string.everyday))
        listElements.add(getString(R.string.every_week))
        listElements.add(getString(R.string.every_month))
        listElements.add(getString(R.string.every_two_months))
        listElements.add(getString(R.string.every_three_months))
        listElements.add(getString(R.string.every_six_months))
        listElements.add(getString(R.string.every_year))

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listElements)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        constrantExpenseSpinner.adapter = adapter
        constrantExpenseSpinner.setSelection(0)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        checkConstrantExpense.setOnCheckedChangeListener { compoundButton, b ->
            if(b){

                howOftenToPay.visibility=View.VISIBLE
                constrantExpenseSpinner.visibility= View.VISIBLE
            }else{
                howOftenToPay.visibility=View.GONE
                constrantExpenseSpinner.visibility= View.GONE

            }
        }
    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    fun addPhotoReceipt(view: View){

        val dialog = PhotoDialog()
        dialog.pm=packageManager
        dialog.activity = this
        dialog.show(supportFragmentManager, "dialog1")
    }

    fun refresh(){

        if(photo!=null){

            imageViewExpense.setImageBitmap(photo)
        }

    }

    fun AcceptExpense(view: View){

        if(ExpenseName.text.isEmpty()){

            Toast.makeText(this, getString(R.string.allert_name), Toast.LENGTH_SHORT).show()
            return
        }

        if(checkConstrantExpense.isChecked==true){

            var constrantExpense = ConstrantExpense()
            constrantExpense.expenseName = ExpenseName.text.toString()

            if (!ShoppingPrice.text.isEmpty())
                constrantExpense.price = ShoppingPrice.text.toString().toDouble()

            constrantExpense.shoppingDate = ShoppingDate.text.toString()
            constrantExpense.setBitmapPhoto(photo)
            constrantExpense.id = constrantExpense!!.id

            constrantExpense.timeToPayExpense = ConstrantExpenseTime.values().first { it.value == constrantExpenseSpinner.selectedItemPosition }

            DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().insert(constrantExpense)
        }else {

            var expense = Expense()
            expense.expenseName = ExpenseName.text.toString()

            if (!ShoppingPrice.text.isEmpty())
                expense.price = ShoppingPrice.text.toString().toDouble()

            expense.shoppingDate = ShoppingDate.text.toString()
            expense.setBitmapPhoto(photo)
            expense.id = expense!!.id

            DatabaseRoom.getAppDataBase()!!.expenseDAO().insert(expense)

        }

        finish()
    }


}
