package com.jvillad1.letscook.presentation.model

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.jvillad1.letscook.R
import com.jvillad1.letscook.presentation.adapters.RecipesController

/**
 * EpoxyModelClass for the Recipes list.
 *
 * @author juan.villada
 */
@EpoxyModelClass(layout = R.layout.item_recipe)
abstract class RecipeModel : EpoxyModelWithHolder<RecipeModel.RecipesHolder>() {

    @EpoxyAttribute
    lateinit var recipeUI: RecipeUI
    @EpoxyAttribute
    lateinit var recipeClickedListener: RecipesController.RecipeClickedListener

    override fun bind(holder: RecipesHolder) = with(holder) {
        titleTextView.text = recipeUI.title

        container.setOnClickListener {
            recipeClickedListener.onRecipeClicked(recipeUI)
        }
    }

    inner class RecipesHolder : EpoxyHolder() {

        lateinit var titleTextView: TextView
        lateinit var container: View

        override fun bindView(itemView: View) {
            titleTextView = itemView.findViewById(R.id.titleTextView)
            container = itemView
        }
    }
}
