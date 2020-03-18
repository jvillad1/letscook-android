package com.jvillad1.letscook.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class for Recipe UI entity.
 *
 * @author juan.villada
 */
@Parcelize
data class RecipeDetailsUI(
    val id: Int,
    val title: String,
    val rating: Int,
    val image: String,
    val instructions: String
) : Parcelable
