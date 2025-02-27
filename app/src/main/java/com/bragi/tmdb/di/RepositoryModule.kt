package com.bragi.tmdb.di

import com.bragi.tmdb.data.remote.service.TmdbApiService
import com.bragi.tmdb.data.repository.MovieRepositoryImpl
import com.bragi.tmdb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(apiService: TmdbApiService): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }
}