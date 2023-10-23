package com.bcan.satelliteexplorer.di

import com.bcan.satelliteexplorer.data.repository.SatelliteRepository
import com.bcan.satelliteexplorer.data.repository.SatelliteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryBindModule {

    @Binds
    abstract fun bindSatelliteRepository(
        repositoryImpl: SatelliteRepositoryImpl
    ): SatelliteRepository

}