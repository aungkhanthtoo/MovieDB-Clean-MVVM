package me.htookyaw.domain.data

import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.ResultFlow

interface MovieRepository {

    fun getMovie(id: Int): ResultFlow<Movie>

    fun getAllMovies(): ResultFlow<List<Movie>>

    fun refreshMovies(): ResultFlow<Unit>

    suspend fun favoriteMovie(id: Int, favorite: Boolean)
}
