package com.jvillad1.letscook.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jvillad1.letscook.R
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity for the Main Entry-Point.
 *
 * @author juan.villada
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // TODO: Observe ViewModel
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        toolbar.title = getText(R.string.recipes)

        replaceFragment(RecipesFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}
