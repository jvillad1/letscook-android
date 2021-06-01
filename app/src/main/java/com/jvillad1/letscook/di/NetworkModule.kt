package com.jvillad1.letscook.di

import com.jvillad1.letscook.data.remote.RecipesApi
import com.jvillad1.letscook.data.remote.RemoteSettings.API_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * [Module] to provide network level dependencies.
 *
 * @author juan.villada
 */
@Module(includes = [CoreNetworkModule::class])
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Api.
     * @Provides annotated dependencies
     */
    @Provides
    internal fun providesOkHttpClient(
        builder: OkHttpClient.Builder
    ): OkHttpClient = builder
        .build()

    @Provides
    @Singleton
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    internal fun providesRecipesApi(retrofit: Retrofit): RecipesApi =
        retrofit.create(RecipesApi::class.java)
}
