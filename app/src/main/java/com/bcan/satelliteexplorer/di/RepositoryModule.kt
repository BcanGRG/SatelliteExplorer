package com.bcan.satelliteexplorer.di

import android.content.Context
import com.bcan.satelliteexplorer.data.repository.SatelliteRepository
import com.bcan.satelliteexplorer.data.repository.SatelliteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideSatelliteRepository(context: Context): SatelliteRepository {
        return SatelliteRepositoryImpl(context)
    }
}