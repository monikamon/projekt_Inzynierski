package pl.polsl.project.activityClasses

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.monika.inzynierka.R
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.tools.DatabaseRoom


//TODO
//sortowanie
//wyszukiwanie paragonu
//wykresy

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_PARAMETER")
class MainScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        DatabaseRoom.getAppDataBase(this)

        if(DatabaseRoom.getAppDataBase()!!.categoryDAO().getAll().isEmpty()){

            DatabaseRoom.getAppDataBase()!!.categoryDAO().insert(Category(getString(R.string.no_category)))
            DatabaseRoom.getAppDataBase()!!.categoryDAO().insert(Category(getString(R.string.bills)))
            DatabaseRoom.getAppDataBase()!!.categoryDAO().insert(Category(getString(R.string.home)))
            DatabaseRoom.getAppDataBase()!!.categoryDAO().insert(Category(getString(R.string.travel)))
            DatabaseRoom.getAppDataBase()!!.categoryDAO().insert(Category(getString(R.string.entertainment)))

        }

    }


    fun showStatistics(view: View){

        val intent=Intent(this, StatisticsActivity::class.java)
        startActivity(intent)

    }

    fun showExpense(view: View){

        val intent=Intent(this, ListExpenseActivity::class.java)
        startActivity(intent)
    }
}

