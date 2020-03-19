package com.jvillad1.letscook.data

import com.jvillad1.letscook.commons.base.Output
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
    private val recipesRemoteDataSource: RecipesRemoteDataSource
) : RecipesRepository {

    override suspend fun getRecipes(): Output<List<RecipeUI>> {
        Timber.d("getRecipes")
        return recipesRemoteDataSource.getRecipes()
    }
}
