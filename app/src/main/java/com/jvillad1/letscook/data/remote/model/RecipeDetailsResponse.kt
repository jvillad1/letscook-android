package com.jvillad1.letscook.data.remote.model

import com.squareup.moshi.Json


/**
 * Data class for Recipe Details data entity (network DTO).
 *
 * @author juan.villada
 */
data class RecipeDetailsResponse(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "rating")
    val rating: Int,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "instructions")
    val instructions: String
)
