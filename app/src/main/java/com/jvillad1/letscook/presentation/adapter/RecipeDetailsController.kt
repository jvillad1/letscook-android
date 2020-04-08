package com.jvillad1.letscook.presentation.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.jvillad1.letscook.presentation.model.RecipeDetailsUI
import com.jvillad1.letscook.presentation.model.recipeDetails

/**
 * Epoxy Controller Class for the Recipe Details.
 *
 * @author juan.villada
 */
class RecipeDetailsController() : TypedEpoxyController<RecipeDetailsUI>() {

    override fun buildModels(recipeDetailsUI: RecipeDetailsUI) {
        recipeDetails {
            id(recipeDetailsUI.id)
            recipeDetailsUI(recipeDetailsUI)
        }
    }
}
