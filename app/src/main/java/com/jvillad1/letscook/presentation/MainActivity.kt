package com.jvillad1.letscook.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

/**
 * Activity for the Main Entry-Point.
 *
 * @author juan.villada
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // ViewModel
    private val recipesViewModel by viewModels<RecipesViewModel>()

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
        super.onCreate(savedInstanceState)

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
