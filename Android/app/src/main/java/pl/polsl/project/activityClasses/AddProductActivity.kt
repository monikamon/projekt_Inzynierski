package pl.polsl.project.activityClasses

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_product.*
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.ConstrantExpense
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.dataStructure.Product
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.databaseStructure.tools.interfaces.ReturnPhotoInterface
import pl.polsl.project.dialogsFragments.PhotoDialog
import java.text.SimpleDateFormat



@Suppress("UNUSED_PARAMETER", "LiftReturnOrAssignment")
open class AddProductActivity : AppCompatActivity(), ReturnPhotoInterface {

    //klasa odpowiadająca za dodawanie produktu przez użytkownika (wpisywanie informacji na ekranie)
    var expense: Expense? = null
    var photo: Bitmap? =null

    //funkcja dodająca zdjęcie do bazy danych
    override fun returnPhoto(pictureBitmap: Bitmap) {
        photo = scalePhoto(pictureBitmap)
        refresh()
    }

    //funkcja, która uruchamiana jest przy uruchomieniu ekranu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        val serial = intent.getSerializableExtra("ExpanseName")

        if(serial!= null)
            expense= serial as Expense

    }

    //powrót do poprzedniego ekranu przez naciśniecie strzałki
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    //dodanie zdjecia przez użytkownika, uruchomienie dialogu
    fun addPhoto(view: View){

        val dialog = PhotoDialog()
        dialog.pm=packageManager
        dialog.activity = this
        dialog.show(supportFragmentManager, "dialog1")

    }

    //ustawnienei na nowo zdjecia
    open fun refresh(){

        if(photo!=null){

            imageView.setImageBitmap(photo)
        }

    }

    //wznowienie ekranu przy powrocie
    override fun onResume(){
        super.onResume()
        refresh()
    }

    //zaakceptopwanie wprowadzonych informacji w produkcie
    open fun acceptProductButton(view: View){

        if(ProductName.text.isEmpty()){

            Toast.makeText(this, getString(R.string.allert_name), Toast.LENGTH_SHORT).show()
            return
        }

        if(!GuarrantyDate.text.isEmpty() && !isValidDate(GuarrantyDate.text.toString())){
            Toast.makeText(this, getString(R.string.invalid_date), Toast.LENGTH_SHORT).show()
            return
        }

        if(!checkPrice(ProductPrize.text.toString())){

            Toast.makeText(this, getString(R.string.moneyFormat), Toast.LENGTH_SHORT).show()
            return
        }

        val product = Product()
        product.name = ProductName.text.toString()

        if(!ProductPrize.text.isEmpty())
            product.prise = ProductPrize.text.toString().toDouble()

        product.guarrantyDate = GuarrantyDate.text.toString()
        product.setBitmapPhoto(photo)

        if(expense is ConstrantExpense)
            product.expenseConstraintId = expense!!.id
        else
            product.expenseId = expense!!.id

        DatabaseRoom.getAppDataBase()!!.productDAO().insert(product)

        finish()
    }

    //sprawdzenie wpisanej ceny przez użytkownika, użycie wyrażenie regularnego
    fun checkPrice(text:String):Boolean{

        if(!text.matches("^[0-9]+([.][0-9]{1,2})?$".toRegex())){
            return false
        }
        return true
    }

    //sprawdzenie poprawności daty gwarancji, przez użycie wyrażenia regularnego
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
