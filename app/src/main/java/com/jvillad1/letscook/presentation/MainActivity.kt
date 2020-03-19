package com.jvillad1.letscook.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jvillad1.letscook.R
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Activity for the Main Entry-Point.
 *
 * @author juan.villada
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // ViewModel
    @Inject
    lateinit var recipesViewModelFactory: RecipesViewModelFactory
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // ViewModel
        recipesViewModel = ViewModelProvider(this, recipesViewModelFactory)
            .get(RecipesViewModel::class.java)

        // TODO: Observe navigation changes
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
