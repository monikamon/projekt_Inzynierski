package pl.polsl.project.activityClasses

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.dialogsFragments.AddCategoryDialog
import pl.polsl.project.dialogsFragments.DeleteCategoryDialog
import kotlinx.android.synthetic.main.activity_category.*
import pl.polsl.project.dialogsFragments.EditCategoryDialog
import kotlin.collections.ArrayList

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_PARAMETER")
class CategoryActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    //klasa, która odpowiada za informacje uzupełniane przez użytkownika na ekranie dotyczącym kategorii.
    var list:ArrayList<Category>?=null
    private var choosenElement: Category?=null

    //funcka uruchamiana przy uruchomieniu ekranu dla kategorii
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

    //wybranie opcji po naciśnięciu na wybraną kategorię z listy
    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.editButton ->{

                val dialog = EditCategoryDialog()
                dialog.activity = this
                dialog.category = choosenElement
                dialog.show(supportFragmentManager, "editCategory")
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

    //dodanie kategorii do listy kategorii
    fun addCategory(view: View){

        val dialog = AddCategoryDialog()
        dialog.activity = this
        dialog.show(supportFragmentManager, "dialog1")
    }

    //zatwierdzenie dodania kategorii
    override fun onSupportNavigateUp():Boolean{

        finish()
        return true
    }

    //powrót do ekranu
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

        //zainicjowanie listy elementami
        val listItems = ArrayList<String>(0)

        for (element in list!!) {
            listItems.add(element.name)
        }

        val adapter =  ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listItems)
        CategoryList.adapter = adapter


    }
}
