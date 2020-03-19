package com.jvillad1.letscook.di

import android.app.Application
import androidx.room.Room
import com.jvillad1.letscook.data.cache.database.RecipesDatabase
import com.jvillad1.letscook.data.cache.database.dao.RecipesDao
import dagger.Module
import dagger.Provides

/**
 * [Module] to provide cache/local level dependencies.
 *
 * @author juan.villada
 */
@Module
object CacheModule {

    @Provides
    @JvmStatic
    internal fun providesRecipesDatabase(context: Application): RecipesDatabase =
        Room.databaseBuilder(context, RecipesDatabase::class.java, "recipes_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @JvmStatic
    fun providesAppAccessTokenDao(recipesDatabase: RecipesDatabase): RecipesDao =
        recipesDatabase.recipesDao()
}
