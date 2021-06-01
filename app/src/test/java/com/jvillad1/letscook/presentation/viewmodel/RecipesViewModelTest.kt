package com.jvillad1.letscook.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.commons.base.UIState
import com.jvillad1.letscook.domain.usecase.GetRecipeDetails
import com.jvillad1.letscook.domain.usecase.GetRecipes
import com.jvillad1.letscook.domain.usecase.SearchRecipes
import com.jvillad1.letscook.presentation.model.RecipeUI
import com.jvillad1.letscook.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Test class for the [RecipesViewModel].
 *
 * @author juan.villada
 */
@ExperimentalCoroutinesApi
class RecipesViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var getRecipes: GetRecipes

    @MockK
    private lateinit var getRecipeDetails: GetRecipeDetails

    @MockK
    private lateinit var searchRecipes: SearchRecipes

    lateinit var recipesViewModel: RecipesViewModel

    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)

        recipesViewModel = RecipesViewModel(
            getRecipes = getRecipes,
            getRecipeDetails = getRecipeDetails,
            searchRecipes = searchRecipes
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `getRecipes returns success with a non empty list`() = runBlockingTest {
        coEvery { getRecipes.invoke() } returns Output.success(getRecipesList())

        recipesViewModel.getRecipes()

        Assert.assertTrue(
            recipesViewModel.currentUIStateLiveData.getOrAwaitValue() == UIState.Data(
                RecipesViewModel.RecipesDataType.RecipesData(getRecipesList())
            )
        )
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

    private fun getRecipesList() = listOf(
        RecipeUI(
            id = 1,
            title = "Test Recipe 1"
        ),
        RecipeUI(
            id = 2,
            title = "Test Recipe 2"
        ),
        RecipeUI(
            id = 3,
            title = "Test Recipe 3"
        )
    )
}
