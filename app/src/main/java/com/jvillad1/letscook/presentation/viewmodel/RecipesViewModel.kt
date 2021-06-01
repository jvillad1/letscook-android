package com.jvillad1.letscook.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.base.NavigationProvider
import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.domain.usecase.RecipesUseCases
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import com.jvillad1.letscook.presentation.model.RecipeUI
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType.RecipeDetailsData
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesDataType.RecipesData
import com.jvillad1.letscook.presentation.viewmodel.RecipesViewModel.RecipesView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [ViewModel] for the Recipes flow.
 *
 * @author juan.villada
 */
@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipesUseCases: RecipesUseCases
) : ViewModel(), NavigationProvider<RecipesView> {

    // Current view LiveData
    private val currentViewMutableLiveData = MutableLiveData<RecipesView>()
    val currentViewLiveData: LiveData<RecipesView>
        get() = currentViewMutableLiveData

    // UIState LiveData
    private val currentUIStateMutableLiveData = MutableLiveData<UIState<RecipesDataType>>()
    val currentUIStateLiveData: LiveData<UIState<RecipesDataType>>
        get() = currentUIStateMutableLiveData

    private var recipes: List<RecipeUI> = listOf()
    private var filteredRecipes: List<RecipeUI> = listOf()

    override fun navigateTo(destinationView: RecipesView) {
        currentViewMutableLiveData.value = destinationView
    }

    init {
        getRecipes()
    }

    fun getRecipes() = viewModelScope.launch {
        Timber.d("getRecipes")
        currentUIStateMutableLiveData.value = UIState.Loading()

        val output = recipesUseCases.getRecipes()
        if (output is Output.Success) {
            recipes = output.data
            currentUIStateMutableLiveData.value = UIState.Data(RecipesData(recipes))
        } else {
            currentUIStateMutableLiveData.value = UIState.Error(R.string.recipes_error_message)
        }
    }

    fun searchRecipes(query: String) = viewModelScope.launch {
        Timber.d("searchRecipes")
        currentUIStateMutableLiveData.value = UIState.Loading()

        fun getQueryString() = "%$query%"

        val output = recipesUseCases.searchRecipes(getQueryString())
        if (output is Output.Success) {
            filteredRecipes = output.data
            currentUIStateMutableLiveData.value = UIState.Data(RecipesData(filteredRecipes))
        } else {
            currentUIStateMutableLiveData.value =
                UIState.Error(R.string.recipes_search_error_message)
        }
    }

    fun clearSearch() {
        currentUIStateMutableLiveData.value = UIState.Data(RecipesData(recipes))
    }

    fun getRecipeDetails(id: Int) = viewModelScope.launch {
        Timber.d("getRecipeDetails")
        currentUIStateMutableLiveData.value = UIState.Loading()

        val output = recipesUseCases.getRecipeDetails(id)
        if (output is Output.Success) {
            currentUIStateMutableLiveData.value = UIState.Data(RecipeDetailsData(output.data))
        } else {
            currentUIStateMutableLiveData.value =
                UIState.Error(R.string.recipe_details_error_message)
        }
    }

    sealed class RecipesView {
        object RecipesFragment : RecipesView()
        data class RecipeDetailsFragment(val recipeUI: RecipeUI) : RecipesView()
    }

    sealed class RecipesDataType {
        data class RecipesData(val recipes: List<RecipeUI>) : RecipesDataType()
        data class RecipeDetailsData(val recipeDetails: RecipeDetailsUI) : RecipesDataType()
    }
}
