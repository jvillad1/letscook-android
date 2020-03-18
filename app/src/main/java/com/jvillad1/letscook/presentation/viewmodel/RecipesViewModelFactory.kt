package com.jvillad1.letscook.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jvillad1.letscook.domain.usecase.RecipesUseCases
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate [RecipesViewModel].
 * Required given ServicesFlowViewModel has a non-empty constructor.
 *
 * @author juan.villada
 */
class RecipesViewModelFactory @Inject constructor(
        private val recipesUseCases: RecipesUseCases
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
            return RecipesViewModel(recipesUseCases) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
