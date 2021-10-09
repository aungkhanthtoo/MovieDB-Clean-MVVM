package me.htookyaw.domain.entitiy

data class Movie(
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
)
