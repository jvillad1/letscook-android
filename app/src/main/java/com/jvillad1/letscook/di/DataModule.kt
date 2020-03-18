package com.jvillad1.letscook.di

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

    // TODO: Bind repository
//    @Binds
//    internal abstract fun providesRecipesRepository(
//        recipesRepositoryImpl: RecipesRepositoryImpl
//    ): RecipesRepository
}
