package com.jvillad1.letscook.domain.usecase

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.domain.repository.RecipesRepository
import com.jvillad1.letscook.presentation.model.RecipeUI
import javax.inject.Inject

/**
 * UseCase for searching for [RecipeUI].
 *
 * @author juan.villada
 */
class SearchRecipes @Inject constructor(private val recipesRepository: RecipesRepository) {

    suspend operator fun invoke(query: String): Output<List<RecipeUI>> = recipesRepository.searchRecipes(query)
}
