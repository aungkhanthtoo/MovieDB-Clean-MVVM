package me.htookyaw.movieapp.ui.movielist

import me.htookyaw.domain.entitiy.Movie

data class MovieUI(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val releaseDate: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val overview: String,
    val originalLanguage: String,
    val posterPath: String,
    val backdropPath: String,
    val genreIds: List<Int>,
    val favorite: Boolean
) {
    companion object
}

fun MovieUI.Companion.of(domain: Movie) = with(domain) {
    MovieUI(
        id,
        title,
        originalTitle,
        releaseDate,
        video,
        voteAverage,
        voteCount,
        popularity,
        adult,
        overview,
        originalLanguage,
        posterPath,
        backdropPath,
        genreIds,
        favorite
    )
}
