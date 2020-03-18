package com.jvillad1.letscook.domain.usecase

import javax.inject.Inject

/**
 * Use Cases for the Recipes flow.
 *
 * @author juan.villada
 */
data class RecipesUseCases @Inject constructor(
    val getRecipes: GetRecipes
)
