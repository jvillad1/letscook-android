package com.jvillad1.letscook.data.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jvillad1.letscook.data.cache.database.dao.RecipesDao
import com.jvillad1.letscook.data.cache.database.entities.RecipeEntity

/**
 * Database class for Recipes related Dao.
 *
 * @author juan.villada
 */
@Database(entities = [RecipeEntity::class], version = 1, exportSchema = false)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
}
