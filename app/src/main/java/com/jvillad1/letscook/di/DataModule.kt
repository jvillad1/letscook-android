package com.jvillad1.letscook.di

import com.jvillad1.letscook.data.RecipesRepositoryImpl
import com.jvillad1.letscook.domain.repository.RecipesRepository
import dagger.Binds
import dagger.Module

/**
 * [Module] to provide data level dependencies.
 *
 * @author juan.villada
 */
@Module(
    includes = [
        NetworkModule::class
    ]
)
abstract class DataModule {

    @Binds
    internal abstract fun providesRecipesRepository(
        recipesRepositoryImpl: RecipesRepositoryImpl
    ): RecipesRepository
}
