package pl.polsl.project.activityClasses

import android.graphics.Bitmap
import pl.polsl.project.dialogsFragments.PhotoDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.monika.inzynierka.R
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.databaseStructure.dataStructure.Expense
import pl.polsl.project.databaseStructure.dataStructure.Product
import pl.polsl.project.databaseStructure.tools.interfaces.returnPhotoInterface
import kotlinx.android.synthetic.main.activity_add_product.*


@Suppress("UNUSED_PARAMETER")
open class AddProductActivity : AppCompatActivity(), returnPhotoInterface {

    var expense: Expense? = null
    var photo: Bitmap? =null

    override fun returnPhoto(pictureBitmap: Bitmap) {

        photo=pictureBitmap
        refresh()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        var serial = intent.getSerializableExtra("ExpanseName")

        if(serial!= null)
            expense= serial as Expense

    }

    //jak naciśnie się na strzałkę u góry, to jest powrót
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

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

    override fun onResume(){
        super.onResume()
        refresh()
    }

    open fun AcceptProductButton(view: View){

        if(ProductName.text.isEmpty()){

            Toast.makeText(this, getString(R.string.allert_name), Toast.LENGTH_SHORT).show()
            return
        }

        var product = Product()
        product.name = ProductName.text.toString()

        if(!ProductPrize.text.isEmpty())
            product.prise = ProductPrize.text.toString().toDouble()

        product.guarrantyDate = GuarrantyDate.text.toString()
        product.setBitmapPhoto(photo)
        product.expenseId = expense!!.id

        DatabaseRoom.getAppDataBase()!!.productDAO().insert(product)

        finish()
    }

}
