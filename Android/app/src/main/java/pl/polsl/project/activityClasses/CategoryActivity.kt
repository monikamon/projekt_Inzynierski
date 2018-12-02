package pl.polsl.project.activityClasses

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import com.example.monika.inzynierka.R
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.dialogsFragments.AddCategoryDialog
import pl.polsl.project.dialogsFragments.DeleteCategoryDialog
import kotlinx.android.synthetic.main.activity_category.*
import kotlin.collections.ArrayList

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_PARAMETER")
class CategoryActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    var list:ArrayList<Category>?=null
    var choosenElement: Category?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        //ustawienie strzałki u góry, aby była znakiem na powrót
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        CategoryList.setOnItemClickListener { adapterView, view, i, l ->
            choosenElement = list!!.get(i)
            val popup = PopupMenu(applicationContext, view)
            popup.menuInflater.inflate(R.menu.edit_delete, popup.menu)
            popup.setOnMenuItemClickListener(this)
            popup.show()
        }

    }

    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.editButton ->{

            }

            R.id.deleteButton ->{

                val dialog = DeleteCategoryDialog()
                dialog.categoryToDelete = choosenElement
                dialog.activity = this
                dialog.show(supportFragmentManager, "categoryDialog")
            }

        }
        return true
    }

    fun AddCategory(view: View){

        val dialog = AddCategoryDialog()
        dialog.activity = this
        dialog.show(supportFragmentManager, "dialog1")
    }

    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    override fun onResume(){
        super.onResume()
        refresh()
    }

    //ustawnienei na nowo zdjecia
    fun refresh(){

        list=ArrayList(DatabaseRoom.getAppDataBase()!!.categoryDAO().getAll())

        var noCategory : Category? = null
        for (element in list!!) {
            if(element.name == getString(R.string.no_category)){
                noCategory = element
                break
            }
        }
        list!!.remove(noCategory)

        //zainicjowanie listy dupiatymi elementami
        val listItems = ArrayList<String>(0)

        for (element in list!!) {
            listItems.add(element.name)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        CategoryList.adapter = adapter


    }
}
