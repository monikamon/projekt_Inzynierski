package pl.polsl.project.activityClasses

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import pl.polsl.project.R
import pl.polsl.project.databaseStructure.dataStructure.Category
import pl.polsl.project.databaseStructure.tools.DatabaseRoom
import pl.polsl.project.databaseStructure.tools.interfaces.ConstraintExpenseAdder

@Suppress("UNUSED_ANONYMOUS_PARAMETER", "UNUSED_PARAMETER")
class MainScreenActivity : AppCompatActivity(), ConstraintExpenseAdder {

    //klasa która odpowiada za główny i pierwszy ekran w aplikacji

    //metoda uruchamiana przy tworzeniu ekranu
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

    //przejście do ekranu ze statystyką
    fun showStatistics(view: View){

        val intent=Intent(this, StatisticsActivity::class.java)
        startActivity(intent)

    }

    //przejście do ekranu z informacjami o wydatku
    fun showExpense(view: View){

        val intent=Intent(this, ListExpensesTabbedActivity::class.java)
        startActivity(intent)
    }
}

