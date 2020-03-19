package com.jvillad1.letscook.data.cache.database.dao

import androidx.room.*
import com.jvillad1.letscook.data.cache.database.entities.RecipeEntity

/**
 * Class that defines AppAccessToken database operations.
 *
 * @author juan.villada
 */
@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Query("SELECT * FROM recipe WHERE recipe.title LIKE :search")
    suspend fun searchRecipes(search: String?): List<RecipeEntity>?
}
