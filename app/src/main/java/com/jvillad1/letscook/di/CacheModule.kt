package com.jvillad1.letscook.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides

/**
 * [Module] to provide cache/local level dependencies.
 *
 * @author juan.villada
 */
@Module
object CacheModule {

//    @Provides
//    @JvmStatic
//    internal fun providesLoginDatabase(context: Application): RecipesDatabase =
//        Room.databaseBuilder(context, RecipesDatabase::class.java, "recipes_db")
//            .fallbackToDestructiveMigration()
//            .build()
}
