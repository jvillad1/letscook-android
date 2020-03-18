package com.jvillad1.letscook.data.remote

import com.jvillad1.letscook.data.remote.model.RecipeDetailsResponse
import com.jvillad1.letscook.data.remote.model.RecipesResponse
import retrofit2.http.*

/**
 * Interface that provides the Auth API End-Points.
 *
 * @author juan.villada
 */
interface RecipesApi {

    @GET("recipes")
    suspend fun getRecipes(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("version") version: String
    ): List<RecipesResponse>

    @GET("recipes/{id}")
    suspend fun getRecipeDetails(
        @Path("id") id: Int
    ): RecipeDetailsResponse
}
