package com.tuankhaiit.androidfeatureslibrary.data.repository

import com.tuankhaiit.androidfeatureslibrary.domain.repository.SimpleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideSimpleRepository(simpleRepository: SimpleRepositoryImpl): SimpleRepository {
        return simpleRepository
    }
}