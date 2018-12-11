package pl.polsl.project.activityClasses

import android.content.Intent
import android.graphics.Point
import pl.polsl.project.databaseStructure.dataStructure.Product
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import pl.polsl.project.R
import kotlinx.android.synthetic.main.activity_show_product.*
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.dialogsFragments.DisplayBigPhotoDialog

class ShowProductActivity : AppCompatActivity() {


    private var writeProduct: Product = Product()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)

        writeProduct = intent.getSerializableExtra("product") as Product

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //ustawienie aby dany text nie był do edycji tylko do oglądania, jest zablokowany
        ProductName.isEnabled=false
        ProductPrize.isEnabled=false
        ProductGuarrantyDate.isEnabled=false


        //-------------------------------------------------------------------------------

        ProductPhoto.setOnClickListener {

            val dialog = DisplayBigPhotoDialog()
            dialog.photo = writeProduct.getBitmapPhoto()
            val screenSize = Point()
            windowManager!!.defaultDisplay.getSize(screenSize)
            dialog.xSize = screenSize.x
            dialog.ySize = screenSize.y
            dialog.show(supportFragmentManager, "photoBigDialog")
        }

    }

    fun refresh(){
        //wypisywanie na danej pozycji tego tego

        writeProduct = DatabaseRoom.getAppDataBase()!!.productDAO().getProduct(writeProduct.id!!).get(0)

        supportActionBar!!.title =writeProduct.name

        if(writeProduct.getBitmapPhoto()!=null){

            ProductPhoto.setImageBitmap(writeProduct.getBitmapPhoto())
        }

        ProductPrize.setText(writeProduct.prise.toString())
        ProductName.setText(writeProduct.name)
        ProductGuarrantyDate.setText(writeProduct.guarrantyDate)
    }

    override fun onResume(){
        super.onResume()
        refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.edit_delete, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.editButton ->{

                val intent = Intent(this, EditProductActivity::class.java)
                intent.putExtra("ProductName", writeProduct)
                startActivity(intent)
            }

            R.id.deleteButton ->{
                DatabaseRoom.getAppDataBase()!!.productDAO().delete(writeProduct)
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
