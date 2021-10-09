package me.htookyaw.data.repository

import kotlinx.coroutines.flow.map
import me.htookyaw.data.db.*
import me.htookyaw.domain.data.MovieDataSource
import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.Result
import me.htookyaw.domain.usecase.base.ResultFlow
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(
    private val database: MovieDatabase
) : MovieDataSource {

    override fun upComingPopularMovies(): ResultFlow<List<Movie>> {
        return database.movieDao().getAllMovies().map { Result.Success(it.toDomain()) }
    }

    override suspend fun addAllMovies(movies: List<Movie>) {
        database.movieDao().addAll(movies.map { MovieRow.of(it) })
    }

    override suspend fun resetMovies(movies: List<Movie>) {
        database.movieDao().resetAll(movies.map { MovieRow.of(it) })
    }

    override suspend fun favoriteMovie(id: Int, favorite: Boolean) {
        database.movieDao().updateFavorite(FavoriteMovie(id, favorite))
    }

    override fun getMovie(id: Int): ResultFlow<Movie> {
        return database.movieDao().getMovie(id).map { Result.Success(it.toDomain()) }
    }
}
