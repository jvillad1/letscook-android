package com.jvillad1.letscook.domain.repository

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.presentation.model.RecipeUI

/**
 * Repository for Recipes handling.
 *
 * @author juan.villada
 */
interface RecipesRepository {

    suspend fun getRecipes(): Output<List<RecipeUI>>

    suspend fun searchRecipes(query: String): Output<List<RecipeUI>>
}
