package com.jvillad1.letscook.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.commons.extensions.observe
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Fragment for the Recipe Details view.
 *
 * @author juan.villada
 */
class RecipeDetailsFragment : Fragment(R.layout.fragment_recipe_details) {

    // ViewModel
    @Inject
    lateinit var recipesViewModelFactory: RecipesViewModelFactory
    private lateinit var recipesViewModel: RecipesViewModel

    // Navigation
    private val args: RecipeDetailsFragmentArgs by navArgs()
    private val recipe by lazy {
        args.recipe
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewModel
        recipesViewModel = ViewModelProvider(requireActivity(), recipesViewModelFactory)
            .get(RecipesViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observe(recipesViewModel.currentUIStateLiveData, ::onUIStateChange)
        recipesViewModel.getRecipeDetails(recipe.id)
    }

    private fun onUIStateChange(uiState: UIState<RecipesViewModel.RecipesDataType>) {
        // TODO: "Not yet implemented"
    }
}
