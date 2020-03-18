package com.jvillad1.letscook.data.remote

import com.jvillad1.letscook.commons.BaseRemoteDataSource
import com.jvillad1.letscook.commons.Output
import com.jvillad1.letscook.data.RecipesDataMapper
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
) : BaseRemoteDataSource() {

    suspend fun getServices(): Output<List<RecipeUI>> =
        try {
            val servicesResponse = withContext(Dispatchers.IO) {
                recipesApi.getRecipes()
            }

            val services = RecipesDataMapper.RecipesListRemoteToUI.map(servicesResponse)
            Output.success(services)
        } catch (e: Throwable) {
            Output.error("Error retrieving the Recipes list from remote: ${e.message}")
        }
}
