package com.jvillad1.letscook.di

import com.jvillad1.letscook.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * [Module] to provide Activities dependencies.
 *
 * @author juan.villada
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity
}
