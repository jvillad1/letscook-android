package com.jvillad1.letscook.domain.usecase

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.domain.repository.RecipesRepository
import com.jvillad1.letscook.presentation.model.RecipeUI
import javax.inject.Inject

/**
 * UseCase for getting the [RecipeUI] list.
 *
 * @author juan.villada
 */
class GetRecipes @Inject constructor(private val recipesRepository: RecipesRepository) {

    suspend operator fun invoke(): Output<List<RecipeUI>> = recipesRepository.getRecipes()
}
