package com.tuankhaiit.androidfeatureslibrary.data.repository

import com.tuankhaiit.androidfeatureslibrary.data.dataSource.remote.service.GithubService
import com.tuankhaiit.androidfeatureslibrary.domain.repository.RepoRepository
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

    @Singleton
    @Provides
    fun provideRepoRepository(): RepoRepository {
        return RepoRepositoryImpl(GithubService.create())
    }
}