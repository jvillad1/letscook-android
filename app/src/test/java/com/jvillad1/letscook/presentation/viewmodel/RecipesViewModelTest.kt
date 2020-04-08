package com.jvillad1.letscook.presentation.viewmodel

import com.jvillad1.letscook.domain.usecase.RecipesUseCases
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Test class for the [RecipesViewModel].
 *
 * @author juan.villada
 */
@RunWith(MockitoJUnitRunner::class)
class RecipesViewModelTest {

    @Mock
    private lateinit var recipesUseCases: RecipesUseCases

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun navigateTo() {
    }

    @Test
    fun getRecipes() = runBlocking {
        val recipesViewModel = RecipesViewModel(recipesUseCases)
        recipesViewModel.getRecipes()
    }

    @Test
    fun searchRecipes() {
    }

    @Test
    fun clearSearch() {
    }

    @Test
    fun getRecipeDetails() {
    }
}
