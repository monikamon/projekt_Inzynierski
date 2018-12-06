package pl.polsl.project.activityClasses

import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.dataStructure.Product
import android.content.Intent
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.tools.ConstrantExpenseTime
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import kotlinx.android.synthetic.main.activity_show_expense.*
import pl.polsl.project.dialogsFragments.DisplayBigPhotoDialog

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_PARAMETER")
class ShowExpenseActivity : AppCompatActivity() {


    var db : DatabaseRoom = DatabaseRoom.getAppDataBase()!!
    var showExpense: Expense = Expense()
    var list:ArrayList<Product> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_expense)

        //blokada klawiatury (zeby nie wyskakiwala)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        showExpense=intent.getSerializableExtra("EXPANSE")as Expense

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ShoppingDate.isEnabled=false
        ShoppingPrize.isEnabled=false
        categoryName.isEnabled=false
        displayEnum.isEnabled=false
        checkConstrantExpense.isEnabled=true

        ListOfProducts.setOnItemClickListener{ adapterView, view, i, l ->
            val intent = Intent(this, ShowProductActivity::class.java)
            intent.putExtra("product", list.get(i))
            startActivity(intent)
        }

        ReceiptPhoto.setOnClickListener {

            val dialog = DisplayBigPhotoDialog()
            dialog.photo = showExpense.getBitmapPhoto()
            var screenSize = Point()
            windowManager!!.getDefaultDisplay().getSize(screenSize)
            dialog.xSize = screenSize.x
            dialog.ySize = screenSize.y
            dialog.show(supportFragmentManager, "photoBigDialog")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.edit_delete, menu)

        return true
    }


    fun refresh(){

        if(showExpense is ConstrantExpense)
            showExpense = DatabaseRoom.getAppDataBase()!!.constrantExpenseDAO().getConstrantExpense(showExpense.id!!).get(0)
        else
            showExpense = DatabaseRoom.getAppDataBase()!!.expenseDAO().getExpense(showExpense.id!!).get(0)

        supportActionBar!!.title =showExpense.expenseName

        if(showExpense.getBitmapPhoto()!=null){

            ReceiptPhoto.setImageBitmap(showExpense.getBitmapPhoto())
        }

        if(showExpense.categoryId==null){

            categoryName.setText(getString(R.string.no_category))
        }else{

            var category = DatabaseRoom.getAppDataBase()!!.categoryDAO().getCategory(showExpense.categoryId!!)
            categoryName.setText(category.get(0).name)
        }

        //sprawdzenie, czy coś jest wybranej klasy
        if(showExpense is ConstrantExpense){
            //ustawienie wartości checkboxa na true!
            checkConstrantExpense.isChecked=true
        }



        if(checkConstrantExpense.isChecked){

            howOftenToPayShow.visibility=View.VISIBLE
            displayEnum.visibility= View.VISIBLE

            when((showExpense as ConstrantExpense).timeToPayExpense){
                ConstrantExpenseTime.EVERYDAY->displayEnum.setText(getString(R.string.everyday))
                ConstrantExpenseTime.EVERY_WEEK->displayEnum.setText(getString(R.string.every_week))
                ConstrantExpenseTime.EVERY_MONTH->displayEnum.setText(getString(R.string.every_month))
                ConstrantExpenseTime.EVERY_TWO_MONTHS->displayEnum.setText(getString(R.string.every_two_months))
                ConstrantExpenseTime.EVERY_THREE_MONTHS->displayEnum.setText(getString(R.string.every_three_months))
                ConstrantExpenseTime.EVERY_SIX_MONTHS->displayEnum.setText(getString(R.string.every_six_months))
                ConstrantExpenseTime.EVERY_YEAR->displayEnum.setText(getString(R.string.every_year))
            }
        }else{
            howOftenToPayShow.visibility=View.GONE
            displayEnum.visibility= View.GONE

        }


        //zainicjowanie listy mniej dupiatymi elementami
        val listItems = ArrayList<String>()
        list.clear()

        if(showExpense is ConstrantExpense)
            list.addAll(db.productDAO().getProductFromConstraintExpense(showExpense.id!!))
        else
            list.addAll(db.productDAO().getProductFromExpense(showExpense.id!!))

        for (element in list) {
            listItems.add(element.name)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        ListOfProducts.adapter = adapter

        //wypisywanie na danej pozycji tego tego
        ShoppingPrize.setText(showExpense.price.toString())
        ShoppingDate.setText(showExpense.shoppingDate)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    fun addProduct(view: View){

        val intent = Intent(this, AddProductActivity::class.java)

        intent.putExtra("ExpanseName", showExpense)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.editButton ->{
                val intent = Intent(this, EditExpenseActivity::class.java)
                intent.putExtra("ExpenseName", showExpense)
                startActivity(intent)
            }

            R.id.deleteButton ->{
                DatabaseRoom.deleteExpenseWithProducts(showExpense)
                finish()
            }

            //jak naciśnie się na strzałkę u góry, to jest powrót
            android.R.id.home -> {
                finish()
            }

        }

        return true
    }

}
