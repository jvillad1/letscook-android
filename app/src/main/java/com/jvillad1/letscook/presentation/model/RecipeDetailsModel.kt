package com.jvillad1.letscook.presentation.model

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jvillad1.letscook.R
import com.jvillad1.letscook.commons.extensions.setRoundCorners
import com.jvillad1.letscook.presentation.model.RecipeDetailsModel.RecipeDetailsHolder

@EpoxyModelClass(layout = R.layout.item_recipe_details)
abstract class RecipeDetailsModel : EpoxyModelWithHolder<RecipeDetailsHolder>() {

    @EpoxyAttribute
    lateinit var recipeDetailsUI: RecipeDetailsUI

    override fun bind(holder: RecipeDetailsHolder) = with(holder) {
        Glide.with(recipeImageView)
            .load(recipeDetailsUI.image)
            .apply(RequestOptions().placeholder(R.color.colorGrayLight))
            .into(holder.recipeImageView)
        recipeImageView.setRoundCorners(R.dimen.margin_x_small)

        recipeTitleTextView.text = recipeDetailsUI.title
        recipeRatingBar.rating = recipeDetailsUI.rating.toFloat()
        recipeInstructionsTextView.text = recipeDetailsUI.instructions
    }


    inner class RecipeDetailsHolder : EpoxyHolder() {

        lateinit var recipeImageView: ImageView
        lateinit var recipeTitleTextView: TextView
        lateinit var recipeRatingBar: RatingBar
        lateinit var recipeInstructionsTextView: TextView

        override fun bindView(itemView: View) {
            recipeImageView = itemView.findViewById(R.id.recipeImageView)
            recipeTitleTextView = itemView.findViewById(R.id.recipeTitleTextView)
            recipeRatingBar = itemView.findViewById(R.id.recipeRatingBar)
            recipeInstructionsTextView = itemView.findViewById(R.id.recipeInstructionsTextView)
        }
    }
}
