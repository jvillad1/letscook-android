package com.jvillad1.letscook.di

import com.jvillad1.letscook.presentation.RecipeDetailsFragment
import com.jvillad1.letscook.presentation.RecipesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * [Module] to provide Fragments dependencies.
 *
 * @author juan.villada
 */
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun bindRecipesFragment(): RecipesFragment

    @ContributesAndroidInjector
    abstract fun bindRecipeDetailsFragment(): RecipeDetailsFragment
}
