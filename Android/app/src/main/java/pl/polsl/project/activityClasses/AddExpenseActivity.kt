package pl.polsl.project.activityClasses

import android.annotation.SuppressLint
import pl.polsl.project.dialogsFragments.PhotoDialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense
import pl.polsl.project.databaseStructure.tools.ConstrantExpenseTime
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.databaseStructure.tools.interfaces.ReturnPhotoInterface
import kotlinx.android.synthetic.main.activity_add_expense.*
import pl.polsl.project.databaseStructure.tools.interfaces.ConstraintExpenseAdder
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


@Suppress("UNUSED_PARAMETER", "LiftReturnOrAssignment")
open class AddExpenseActivity : AppCompatActivity(), ReturnPhotoInterface, ConstraintExpenseAdder {

    //klasa która odpowiada za działania użytkownika na ekranie. Odpowiada za dodawanie wydatku (uzupełnianie informacji o wydatku)
    var photo: Bitmap?=null
    var nameOfCategoryList: List<Category>?=null

    override fun returnPhoto(pictureBitmap: Bitmap) {
        //funkcja która pobiera zdjecie paragonu
        photo = scalePhoto(pictureBitmap)
        refresh()
    }


    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        howOftenToPay.visibility=View.GONE
        constrantExpenseSpinner.visibility= View.GONE



        //uzupelnienie spinera dla kategorii
        val listCategory = ArrayList<String>()
        nameOfCategoryList = DatabaseRoom.getAppDataBase()!!.categoryDAO().getAll()

        for(element in nameOfCategoryList!!){

            listCategory.add(element.name)
        }

        val adapterCategory = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCategory)
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        categorySpinner.adapter = adapterCategory
        categorySpinner.setSelection(0)


        //uzupelnienie spinnera dla stalego wydatku (czestotliwosc placenia)
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

        checkConstrantExpense.setOnCheckedChangeListener { _, b ->
            if(b){

                howOftenToPay.visibility=View.VISIBLE
                constrantExpenseSpinner.visibility= View.VISIBLE
            }else{
                howOftenToPay.visibility=View.GONE
                constrantExpenseSpinner.visibility= View.GONE

            }
        }

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        ShoppingDate.setText(currentDate)

    }

    //powrót do ekranu po naciśnięciu strzałki
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    //dodanie zdjecia paragonu
    fun addPhotoReceipt(view: View){

        val dialog = PhotoDialog()
        dialog.pm=packageManager
        dialog.activity = this
        dialog.show(supportFragmentManager, "dialog1")
    }

    //odświeżenie ekranu przez ustawienie zdjęcia
    open fun refresh(){

        if(photo!=null){

            imageViewExpense.setImageBitmap(photo)
        }

    }

    //metoda uruchamiana podczas zaakceptowania wpisanych informacji o wydatku
    open fun acceptExpense(view: View){

        if(ExpenseName.text.isEmpty()){

            Toast.makeText(this, getString(R.string.allert_name), Toast.LENGTH_SHORT).show()
            return
        }

        if(ShoppingDate.text.isEmpty()){

            Toast.makeText(this, getString(R.string.no_date), Toast.LENGTH_SHORT).show()
            return
        }

        if(!isValidDate(ShoppingDate.text.toString())){
            Toast.makeText(this, getString(R.string.invalid_date), Toast.LENGTH_SHORT).show()
            return
        }

        if(!checkPrice(ShoppingPrice.text.toString())){

            Toast.makeText(this, getString(R.string.moneyFormat), Toast.LENGTH_SHORT).show()
            return
        }



        if(checkConstrantExpense.isChecked){

            val constrantExpense = ConstrantExpense()
            constrantExpense.expenseName = ExpenseName.text.toString()

            if (!ShoppingPrice.text.isEmpty()) {


                constrantExpense.price = ShoppingPrice.text.toString().toDouble()
            }

            constrantExpense.shoppingDate = ShoppingDate.text.toString()
            constrantExpense.setBitmapPhoto(photo)
            constrantExpense.categoryId= nameOfCategoryList!!.get(categorySpinner.selectedItemPosition).id

            constrantExpense.timeToPayExpense = ConstrantExpenseTime.values().first { it.value == constrantExpenseSpinner.selectedItemPosition }

            DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().insert(constrantExpense)

            checkConstraintExpense()
        }else {

            val expense = Expense()
            expense.expenseName = ExpenseName.text.toString()

            if (!ShoppingPrice.text.isEmpty())
                expense.price = ShoppingPrice.text.toString().toDouble()

            expense.shoppingDate = ShoppingDate.text.toString()
            expense.setBitmapPhoto(photo)
            expense.categoryId= nameOfCategoryList!!.get(categorySpinner.selectedItemPosition).id

            DatabaseRoom.getAppDataBase()!!.expenseDAO().insert(expense)

        }

        finish()
    }

    // funkcja, która sprawdza czy podana cena została wpisana poprawnie (zostało użyte wyrażenie regularne do tego)
    fun checkPrice(text:String):Boolean{

        if(!text.matches("^[0-9]+([.][0-9]{1,2})?$".toRegex())){
            return false
        }
        return true
    }

    //funkcja, która sprawdza czy podana data została wpisana poprawnie (zostało użyte wyrażenie regularne do tego)
    @SuppressLint("SimpleDateFormat")
    fun isValidDate(text:String): Boolean {

        if(!text.matches("^[0-9]{2}[/][0-9]{2}[/][0-9]{4}$".toRegex())){
            return false
        }

        val df = SimpleDateFormat("dd/MM/yy")
        df.isLenient = false
        try {
            df.parse(text)
            return true
        } catch (e: Exception){
            return false
        }

    }


}
