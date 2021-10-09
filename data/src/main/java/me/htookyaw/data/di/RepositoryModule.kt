package me.htookyaw.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.htookyaw.data.repository.LocalMovieDataSource
import me.htookyaw.data.repository.MovieListRepositoryImpl
import me.htookyaw.data.repository.RemoteMovieDataSource
import me.htookyaw.domain.data.MovieDataSource
import me.htookyaw.domain.data.MovieRepository
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Named("localMovieDataSource")
    abstract fun bindLocalMovieDatasource(dataSource: LocalMovieDataSource): MovieDataSource

    @Binds
    @Named("remoteMovieDataSource")
    abstract fun bindRemoteMovieDatasource(dataSource: RemoteMovieDataSource): MovieDataSource

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repository: MovieListRepositoryImpl): MovieRepository
}
