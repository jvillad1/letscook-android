package com.jvillad1.letscook.di

import android.content.Context
import androidx.room.Room
import com.jvillad1.letscook.data.cache.database.RecipesDatabase
import com.jvillad1.letscook.data.cache.database.dao.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * [Module] to provide cache/local level dependencies.
 *
 * @author juan.villada
 */
@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    @Singleton
    internal fun providesRecipesDatabase(@ApplicationContext context: Context): RecipesDatabase =
        Room.databaseBuilder(context, RecipesDatabase::class.java, "recipes_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesAppAccessTokenDao(recipesDatabase: RecipesDatabase): RecipesDao =
        recipesDatabase.recipesDao()
}
