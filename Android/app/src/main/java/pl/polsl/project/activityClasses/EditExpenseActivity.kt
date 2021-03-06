package pl.polsl.project.activityClasses

import android.os.Bundle
import android.view.View
import android.widget.Toast
import pl.polsl.project.R
import kotlinx.android.synthetic.main.activity_add_expense.*
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.tools.ConstrantExpenseTime
import pl.polsl.project.databaseStructure.tools.DatabaseRoom


@Suppress("UNUSED_PARAMETER", "LiftReturnOrAssignment")
open class EditExpenseActivity : AddExpenseActivity() {


    // klasa w której zaimplementowano edycję ekranu wydatku
    var expense: Expense?=null


    // funkcja uruchamiana przy uruchomieniu ekranu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        expense=intent.getSerializableExtra("ExpenseName") as Expense

        if(expense is ConstrantExpense)
            expense = DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getConstrantExpense(expense!!.id!!).get(0)
        else
            expense = DatabaseRoom.getAppDataBase()!!.expenseDAO().getExpense(expense!!.id!!).get(0)

        checkConstrantExpense.isClickable = false

        if(expense is ConstrantExpense){
            howOftenToPay.visibility=View.VISIBLE
            constrantExpenseSpinner.visibility= View.VISIBLE
            checkConstrantExpense.isChecked = true
        }else{
            howOftenToPay.visibility=View.GONE
            constrantExpenseSpinner.visibility= View.GONE
            checkConstrantExpense.isChecked = false
        }

        photo = expense!!.getBitmapPhoto()
        refresh()
    }

    //funkcja, której zadaniem jest odświeżanie
    override fun refresh(){
        super.refresh()

        ExpenseName.setText(expense!!.expenseName)
        ShoppingPrice.setText(expense!!.price.toString())
        ShoppingDate.setText(expense!!.shoppingDate)

        var index = 0
        for(elem in nameOfCategoryList!!){
            if(elem.id == expense!!.categoryId){
                categorySpinner.setSelection(index)
                break
            }
            index += 1
        }

        if(expense is ConstrantExpense)
            constrantExpenseSpinner.setSelection((expense!! as ConstrantExpense).timeToPayExpense.value)


    }

    //funkcja, w której zostają zaakceptowane edytowane zmiany przez użytkownika
    override fun acceptExpense(view: View){

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

            val constrantExpense = expense as ConstrantExpense
            constrantExpense.expenseName = ExpenseName.text.toString()

            if (!ShoppingPrice.text.isEmpty())
                constrantExpense.price = ShoppingPrice.text.toString().toDouble()

            constrantExpense.shoppingDate = ShoppingDate.text.toString()
            constrantExpense.setBitmapPhoto(photo)
            constrantExpense.categoryId= nameOfCategoryList!!.get(categorySpinner.selectedItemPosition).id

            constrantExpense.timeToPayExpense = ConstrantExpenseTime.values().first { it.value == constrantExpenseSpinner.selectedItemPosition }

            DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().insert(constrantExpense)

            checkConstraintExpense()
        }else {


            expense!!.expenseName = ExpenseName.text.toString()

            if (!ShoppingPrice.text.isEmpty())
                expense!!.price = ShoppingPrice.text.toString().toDouble()

            expense!!.shoppingDate = ShoppingDate.text.toString()
            expense!!.setBitmapPhoto(photo)
            expense!!.categoryId= nameOfCategoryList!!.get(categorySpinner.selectedItemPosition).id

            DatabaseRoom.getAppDataBase()!!.expenseDAO().insert(expense!!)

        }

        finish()
    }





}
