package com.jvillad1.letscook.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.extensions.observe
import com.jvillad1.letscook.presentation.model.RecipeUI
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

    private lateinit var appBarConfiguration: AppBarConfiguration

    // Navigation
    private val recipesNavController: NavController by lazy {
        findNavController(R.id.recipesNavHostFragment)
    }

    private val onDestinationChangedListener = NavController.OnDestinationChangedListener { _, destination, arguments ->
        when (destination.id) {
            R.id.recipeDetailsFragment -> {
                toolbar.title = getText(R.string.recipe_details)
            }
            else -> {
                toolbar.title = getText(R.string.recipes)
            }
        }
    }

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

        appBarConfiguration = AppBarConfiguration(setOf(R.id.recipesFragment))
        setupActionBarWithNavController(recipesNavController, appBarConfiguration)
        recipesNavController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        return recipesNavController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun onRecipesViewChange(destination: RecipesView) {
        Timber.d("onRecipesViewChange")
        when (destination) {
            is RecipesView.RecipesFragment -> showRecipesFragment()
            is RecipeDetailsFragment -> showRecipeDetailsFragment(destination.recipeUI)
        }
    }

    private fun showRecipesFragment() {
        Timber.d("showRecipesFragment")
        recipesNavController.navigate(R.id.recipesFragment)
    }

    private fun showRecipeDetailsFragment(recipeUI: RecipeUI) {
        Timber.d("showRecipeDetailsFragment")
        val action = RecipesFragmentDirections.actionRecipesFragmentToRecipeDetailsFragment(recipeUI)
        recipesNavController.navigate(action)
    }
}
