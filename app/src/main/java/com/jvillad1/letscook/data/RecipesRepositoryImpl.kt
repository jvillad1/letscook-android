package com.jvillad1.letscook.data

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.data.cache.RecipesCacheDataSource
import com.jvillad1.letscook.data.remote.RecipesRemoteDataSource
import com.jvillad1.letscook.domain.repository.RecipesRepository
import com.jvillad1.letscook.presentation.model.RecipeUI
import timber.log.Timber
import javax.inject.Inject

/**
 * Recipes related Repository implementation.
 *
 * @author juan.villada
 */
class RecipesRepositoryImpl @Inject constructor(
    private val recipesRemoteDataSource: RecipesRemoteDataSource,
    private val recipesCacheDataSource: RecipesCacheDataSource
) : RecipesRepository {

    override suspend fun getRecipes(): Output<List<RecipeUI>> {
        Timber.d("getRecipes")

        val recipesRemote = recipesRemoteDataSource.getRecipes()

        if (recipesRemote is Output.Success) {
            Timber.d("Save Recipes to local database")
            recipesCacheDataSource.insertRecipes(RecipesDataMapper.RecipesListUIToCache.map(recipesRemote.data))
        }

        return recipesRemoteDataSource.getRecipes()
    }
}
