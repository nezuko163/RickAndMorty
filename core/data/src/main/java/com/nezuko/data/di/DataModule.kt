package com.nezuko.data.di

import com.nezuko.data.repository.RickAndMortyRepository
import com.nezuko.data.repository.RickAndMortyRepositoryImpl
import com.nezuko.data.source.RickAndMortyApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun rickAndMorty(impl: RickAndMortyRepositoryImpl): RickAndMortyRepository
}