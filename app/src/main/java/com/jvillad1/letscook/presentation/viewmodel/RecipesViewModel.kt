package com.jvillad1.letscook.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.domain.usecase.RecipesUseCases
import com.jvillad1.letscook.presentation.model.RecipeUI
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * [ViewModel] for the Recipes flow.
 *
 * @author juan.villada
 */
class RecipesViewModel @Inject constructor(
    private val recipesUseCases: RecipesUseCases
) : ViewModel() {

    // UIState LiveData
    private val currentUIStateMutableLiveData = MutableLiveData<UIState<RecipesDataType>>()
    val currentUIStateLiveData: LiveData<UIState<RecipesDataType>>
        get() = currentUIStateMutableLiveData

    init {
        getRecipes()
    }

    fun getRecipes() {
        viewModelScope.launch {
            Timber.d("getRecipes")
            currentUIStateMutableLiveData.value = UIState.Loading()

            val output = recipesUseCases.getRecipes()
            if (output is Output.Success) {
                currentUIStateMutableLiveData.value = UIState.Data(RecipesDataType.RecipesData(output.data))
            } else {
                currentUIStateMutableLiveData.value = UIState.Error(R.string.recipes_error_message)
            }
        }
    }

    sealed class RecipesDataType {
        data class RecipesData(val recipes: List<RecipeUI>) : RecipesDataType()
    }
}
