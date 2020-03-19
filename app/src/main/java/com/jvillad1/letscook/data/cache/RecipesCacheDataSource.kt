package com.jvillad1.letscook.data.cache

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.data.RecipesDataMapper
import com.jvillad1.letscook.data.cache.database.dao.RecipesDao
import com.jvillad1.letscook.data.cache.database.entities.RecipeEntity
import com.jvillad1.letscook.presentation.model.RecipeUI
import javax.inject.Inject

/**
 * Recipes related cache DataSource.
 *
 * @author juan.villada
 */
class RecipesCacheDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    suspend fun insertRecipes(recipes: List<RecipeEntity>) = recipesDao.insertRecipes(recipes)

    suspend fun searchRecipes(query: String): Output<List<RecipeUI>> {
        return Output.success(RecipesDataMapper.RecipesListCacheToUI.map(recipesDao.searchRecipes(query)))
    }
}
