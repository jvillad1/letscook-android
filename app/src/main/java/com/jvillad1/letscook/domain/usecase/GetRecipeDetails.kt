package com.jvillad1.letscook.domain.usecase

import com.jvillad1.letscook.commons.base.Output
import com.jvillad1.letscook.domain.repository.RecipesRepository
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import javax.inject.Inject

/**
 * UseCase for getting the [RecipeDetailsUI] list.
 *
 * @author juan.villada
 */
class GetRecipeDetails @Inject constructor(private val recipesRepository: RecipesRepository) {

    suspend operator fun invoke(id: Int): Output<RecipeDetailsUI> = recipesRepository.getRecipeDetails(id)
}
