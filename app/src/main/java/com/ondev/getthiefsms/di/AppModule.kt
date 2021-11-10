package com.ondev.getthiefsms.di

import android.content.Context
import com.ondev.getthiefsms.data.PhoneDatabase
import com.ondev.getthiefsms.data.repository.PhoneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        PhoneDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideRepository(
        phoneDatabase: PhoneDatabase
    ) = PhoneRepository(numbersRoomDatabase = phoneDatabase)
}