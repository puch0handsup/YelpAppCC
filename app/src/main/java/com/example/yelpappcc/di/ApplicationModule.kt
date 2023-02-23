package com.example.yelpappcc.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.yelpappcc.data.local.LocalRepositoryImpl
import com.example.yelpappcc.data.local.YelpDAO
import com.example.yelpappcc.data.local.YelpDatabase
import com.example.yelpappcc.domain.repository.LocalRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Provides
    fun providesConnectivityManager(
        @ApplicationContext context: Context
    ): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun providesYelpDao(
        @ApplicationContext context: Context
    ) : YelpDAO = Room.databaseBuilder(
        context,
        YelpDatabase::class.java,
        "yelp-db"
    ).build().dao

    @Provides
    fun providesLocalRepository(
        dao: YelpDAO
    ) : LocalRepository = LocalRepositoryImpl(dao)
}