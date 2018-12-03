package pl.polsl.project.activityClasses

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_expense.*
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.databaseStructure.tools.interfaces.ConstraintExpenseAdder
import java.text.SimpleDateFormat
import java.util.*


//TODO
//1. Kategorie na dni / miesiace / lata / wszystko (po latach)
//2. Wydatki na dni / miesiace / lata / wszystko (po latach)


@Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_PARAMETER")
class MainScreenActivity : AppCompatActivity(), ConstraintExpenseAdder {


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


        checkConstraintExpense()

    }

    fun showStatistics(view: View){

        val intent=Intent(this, StatisticsActivity::class.java)
        startActivity(intent)

    }

    fun showExpense(view: View){

        val intent=Intent(this, ListExpensesTabbedActivity::class.java)
        startActivity(intent)
    }
}

