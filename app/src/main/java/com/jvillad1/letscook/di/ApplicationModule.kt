package com.jvillad1.letscook.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * [Module] to provide Application level dependencies.
 *
 * @author juan.villada
 */
@Module
object ApplicationModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providesConnectivityManager(application: Application): ConnectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}
