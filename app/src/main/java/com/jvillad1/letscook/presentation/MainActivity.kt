package com.jvillad1.letscook.presentation

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.extensions.hideKeyboard
import com.jvillad1.letscook.commons.extensions.observe
import com.jvillad1.letscook.data.remote.model.RecipeDetailsResponse
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesView
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesView.RecipeDetailsFragment
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModelFactory
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
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

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        // ViewModel
        recipesViewModel = ViewModelProvider(this, recipesViewModelFactory)
            .get(RecipesViewModel::class.java)

        observe(recipesViewModel.currentViewLiveData, ::onRecipesViewChange)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        toolbar.title = getText(R.string.recipes)

        replaceFragment(RecipesFragment.newInstance())

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Timber.d("onQueryTextChange")
                if (newText.isEmpty()) {
                    recipesViewModel.clearSearch()
                }

                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Timber.d("onQueryTextSubmit")
                hideKeyboard()
                recipesViewModel.searchRecipes(query)

                return true
            }
        })
    }

    private fun onRecipesViewChange(destination: RecipesView) {
        when (destination) {
            // TODO: Implement this with navigation component
            is RecipesView.RecipesFragment -> Timber.d("Show Recipes")
            is RecipeDetailsFragment -> Timber.d("Show Recipes")
        }
    }

    // TODO: Implement this with navigation component
    private fun replaceFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}
