package com.jvillad1.letscook.data.remote.model

import com.squareup.moshi.Json


/**
 * Data class for Recipes data entity (network DTO).
 *
 * @author juan.villada
 */
data class RecipesResponse(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "title")
    val title: String
)
