package pl.polsl.project.activityClasses

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_list_expenses_tabbed.*
import pl.polsl.project.R
import pl.polsl.project.activityClasses.fragmentsClasses.AllElementsFragment
import pl.polsl.project.activityClasses.fragmentsClasses.SearchFragment
import pl.polsl.project.dialogsFragments.SortingDialog

class ListExpensesTabbedActivity : AppCompatActivity() {

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    private var allelem = AllElementsFragment()
    private var searchelem = SearchFragment()

    companion object {
        var sortType: Int = 5
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_expenses_tabbed)

        setSupportActionBar(toolbar)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = getString(R.string.title_activity_list_expenses_tabbed)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.chooseCategoryOption ->{

                val intent = Intent(this, CategoryActivity::class.java)
                startActivity(intent)
            }

            R.id.sortButton ->{

                val dialog = SortingDialog()
                dialog.activity = this
                dialog.show(supportFragmentManager, "sorting")
            }

            R.id.addButton ->{
                val intent = Intent(this, AddExpenseActivity::class.java)
                startActivity(intent)
            }

            //jak naciśnie się na strzałkę u góry, to jest powrót
            android.R.id.home -> {
                finish()
            }

        }

        return super.onOptionsItemSelected(item)
    }

    fun refresh(){
        searchelem.refresh()
        allelem.refresh()
    }


    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {

            if(position == 0 )
                return allelem
            else
                return searchelem
        }

        override fun getCount(): Int {
            return 2
        }
    }


}
