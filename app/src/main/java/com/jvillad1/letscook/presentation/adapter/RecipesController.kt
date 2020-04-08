package com.jvillad1.letscook.presentation.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.jvillad1.letscook.presentation.model.RecipeUI
import com.jvillad1.letscook.presentation.model.recipe

/**
 * Epoxy Controller Class for the Recipes list.
 *
 * @author juan.villada
 */
class RecipesController(
    private val listener: RecipeClickedListener
) : TypedEpoxyController<List<RecipeUI>>() {

    override fun buildModels(recipes: List<RecipeUI>) {
        recipes.forEach {
            recipe {
                id(it.id)
                recipeUI(it)
                recipeClickedListener(listener)
            }
        }
    }

    interface RecipeClickedListener {
        fun onRecipeClicked(recipeUI: RecipeUI)
    }
}
