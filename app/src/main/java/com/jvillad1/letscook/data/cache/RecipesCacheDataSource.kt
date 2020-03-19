package com.jvillad1.letscook.data.cache

import com.jvillad1.letscook.data.cache.database.dao.RecipesDao
import com.jvillad1.letscook.data.cache.database.entities.RecipeEntity
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
}
