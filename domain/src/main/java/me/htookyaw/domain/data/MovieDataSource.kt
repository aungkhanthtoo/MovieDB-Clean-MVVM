package me.htookyaw.domain.data

import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.ResultFlow

interface MovieDataSource {

    fun upComingPopularMovies(): ResultFlow<List<Movie>>

    suspend fun addAllMovies(movies: List<Movie>)

    suspend fun resetMovies(movies: List<Movie>)

    suspend fun favoriteMovie(id: Int, favorite: Boolean)

    fun getMovie(id: Int): ResultFlow<Movie>
}
