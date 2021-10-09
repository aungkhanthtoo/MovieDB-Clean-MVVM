package me.htookyaw.data.repository

import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import me.htookyaw.domain.data.MovieDataSource
import me.htookyaw.domain.data.MovieRepository
import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.Result
import me.htookyaw.domain.usecase.base.ResultFlow
import me.htookyaw.domain.usecase.base.data
import me.htookyaw.domain.usecase.base.error
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MovieListRepositoryImpl @Inject constructor(
    @Named("remoteMovieDataSource") private val remoteDataSource: MovieDataSource,
    @Named("localMovieDataSource") private val localDataSource: MovieDataSource
) : MovieRepository {

    override fun getAllMovies(): ResultFlow<List<Movie>> = flow {
        emit(localDataSource.upComingPopularMovies().first())

        val remoteResult = remoteDataSource.upComingPopularMovies().firstOrNull()

        remoteResult?.data?.let {
            localDataSource.addAllMovies(it)
        } ?: remoteResult?.let {
            emit(remoteResult)
        }

        emitAll(localDataSource.upComingPopularMovies())
    }

    override fun refreshMovies(): ResultFlow<Unit> = flow {
        val remoteResult = remoteDataSource.upComingPopularMovies().firstOrNull()

        remoteResult?.data?.let {
            localDataSource.resetMovies(it)
        } ?: remoteResult?.error?.let {
            emit(Result.Error(it))
        }
    }

    override suspend fun favoriteMovie(id: Int, favorite: Boolean) {
        localDataSource.favoriteMovie(id, favorite)
    }

    override fun getMovie(id: Int): ResultFlow<Movie> {
        return localDataSource.getMovie(id)
    }
}
