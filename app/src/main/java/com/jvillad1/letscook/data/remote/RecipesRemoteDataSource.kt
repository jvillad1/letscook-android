package com.jvillad1.letscook.data.remote

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.data.RecipesDataMapper
import com.jvillad1.letscook.data.RecipesDataMapper.RecipeDetailsRemoteToUI
import com.jvillad1.letscook.data.RecipesDataMapper.RecipesListRemoteToUI
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import com.jvillad1.letscook.presentation.model.RecipeUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Recipes related remote DataSource.
 *
 * @author juan.villada
 */
class RecipesRemoteDataSource @Inject constructor(
    private val recipesApi: RecipesApi
) {

    suspend fun getRecipes(): Output<List<RecipeUI>> =
        try {
            val recipesResponse = withContext(Dispatchers.IO) {
                recipesApi.getRecipes()
            }

            val recipes = RecipesListRemoteToUI.map(recipesResponse)
            Output.success(recipes)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Recipes list from remote: ${e.message}")
        }

    suspend fun getRecipeDetails(id: Int): Output<RecipeDetailsUI> =
        try {
            val recipeDetailsResponse = withContext(Dispatchers.IO) {
                recipesApi.getRecipeDetails(id)
            }

            val recipeDetails = RecipeDetailsRemoteToUI.map(recipeDetailsResponse)
            Output.success(recipeDetails)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Recipe details from remote: ${e.message}")
        }
}
