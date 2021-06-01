package com.jvillad1.letscook.di

import com.jvillad1.letscook.data.RecipesRepositoryImpl
import com.jvillad1.letscook.domain.repository.RecipesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * [Module] to provide data level dependencies.
 *
 * @author juan.villada
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun providesRecipesRepository(
        recipesRepositoryImpl: RecipesRepositoryImpl
    ): RecipesRepository
}
