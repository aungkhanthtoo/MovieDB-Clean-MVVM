package me.htookyaw.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.htookyaw.domain.entitiy.Movie

@Entity(tableName = "movies")
data class MovieRow(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    val adult: Boolean,
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @SerializedName("title")
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    val popularity: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @SerializedName("video")
    val video: Boolean,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    val favorite: Boolean
) {
    companion object
}

fun List<MovieRow>.toDomain(): List<Movie> = map { it.toDomain() }

fun MovieRow.toDomain() = Movie(
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

fun MovieRow.Companion.of(domain: Movie) = MovieRow(
    domain.id,
    domain.posterPath,
    domain.adult,
    domain.overview,
    domain.releaseDate,
    domain.genreIds,
    domain.originalTitle,
    domain.originalLanguage,
    domain.title,
    domain.backdropPath,
    domain.popularity,
    domain.voteCount,
    domain.video,
    domain.voteAverage,
    domain.favorite
)
