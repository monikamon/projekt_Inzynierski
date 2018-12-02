package pl.polsl.project.activityClasses

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.monika.inzynierka.R
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.databaseStructure.dataStructure.Product
import kotlinx.android.synthetic.main.activity_add_product.*


@Suppress("UNUSED_PARAMETER")
class EditProductActivity : AddProductActivity(){

    var product:Product?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product=intent.getSerializableExtra("ProductName") as Product

        photo = product!!.getBitmapPhoto()
        refresh()

    }

    override fun refresh(){
        super.refresh()

        ProductName.setText(product!!.name)
        ProductPrize.setText(product!!.prise.toString())
        GuarrantyDate.setText(product!!.guarrantyDate)

    }

    override fun AcceptProductButton(view: View){

        if(ProductName.text.isEmpty()){

            Toast.makeText(this, getString(R.string.allert_name), Toast.LENGTH_SHORT).show()
            return
        }

        product!!.name = ProductName.text.toString()

        if(!ProductPrize.text.isEmpty())
            product!!.prise = ProductPrize.text.toString().toDouble()

        product!!.guarrantyDate = GuarrantyDate.text.toString()
        product!!.setBitmapPhoto(photo)

        DatabaseRoom.getAppDataBase()!!.productDAO().insert(product!!)

        finish()
    }

}
