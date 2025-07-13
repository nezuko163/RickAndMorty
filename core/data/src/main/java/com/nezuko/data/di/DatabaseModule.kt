package com.nezuko.data.di

import android.content.Context
import com.nezuko.data.db.AppDB
import com.nezuko.data.db.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDB {
        return AppDB.getDatabase(context)
    }

    @Provides
    fun provideDao(database: AppDB): CharacterDao {
        return database.characterDao()
    }
}