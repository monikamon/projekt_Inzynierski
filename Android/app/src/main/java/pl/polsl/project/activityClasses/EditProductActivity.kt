package pl.polsl.project.activityClasses

import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_product.*
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Product
import pl.polsl.project.databaseStructure.tools.DatabaseRoom


@Suppress("UNUSED_PARAMETER")
class EditProductActivity : AddProductActivity(){

    private var product:Product?=null


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

    override fun acceptProductButton(view: View){

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

        product!!.name = ProductName.text.toString()

        if(!ProductPrize.text.isEmpty())
            product!!.prise = ProductPrize.text.toString().toDouble()

        product!!.guarrantyDate = GuarrantyDate.text.toString()
        product!!.setBitmapPhoto(photo)

        DatabaseRoom.getAppDataBase()!!.productDAO().insert(product!!)

        finish()
    }

}
