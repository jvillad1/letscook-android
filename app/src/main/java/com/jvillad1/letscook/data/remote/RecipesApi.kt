package com.jvillad1.letscook.data.remote

import com.jvillad1.letscook.data.remote.model.RecipeDetailsResponse
import com.jvillad1.letscook.data.remote.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface that provides the Recipes API End-Points.
 *
 * @author juan.villada
 */
interface RecipesApi {

    @GET("recipes")
    suspend fun getRecipes(): List<RecipeResponse>

    @GET("recipes/{id}")
    suspend fun getRecipeDetails(
        @Path("id") id: Int
    ): RecipeDetailsResponse
}
