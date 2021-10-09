package me.htookyaw.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAll(movies: List<MovieRow>)

    @Query("SELECT * FROM movies")
    abstract fun getAllMovies(): Flow<List<MovieRow>>

    @Query("SELECT * FROM movies WHERE id=:id")
    abstract fun getMovie(id: Int): Flow<MovieRow>

    @Query("DELETE FROM movies")
    abstract suspend fun clear()

    @Update(entity = MovieRow::class)
    abstract suspend fun updateFavorite(favorite: FavoriteMovie): Int

    @Transaction
    open suspend fun resetAll(movies: List<MovieRow>) {
        clear()
        addAll(movies)
    }
}
