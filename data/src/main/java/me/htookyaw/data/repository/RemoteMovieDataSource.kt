package me.htookyaw.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import me.htookyaw.data.network.MovieApi
import me.htookyaw.data.network.response.toDomain
import me.htookyaw.data.utils.MovieListException
import me.htookyaw.data.utils.emitNetworkError
import me.htookyaw.data.utils.emitResult
import me.htookyaw.domain.data.MovieDataSource
import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.IoDispatcher
import me.htookyaw.domain.usecase.base.Result
import me.htookyaw.domain.usecase.base.ResultFlow
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(
    private val apiService: MovieApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieDataSource {

    override fun upComingPopularMovies(): ResultFlow<List<Movie>> {
        return flow {
            emitResult(sumTwoMovieLists().map { it.toDomain() })
        }
            .catch { handleMovieListExceptions(it) }
    }

    override fun getMovie(id: Int): ResultFlow<Movie> = flow {
    }

    override suspend fun addAllMovies(movies: List<Movie>) {
    }

    override suspend fun favoriteMovie(id: Int, favorite: Boolean) {
    }

    override suspend fun resetMovies(movies: List<Movie>) {
    }

    private suspend fun sumTwoMovieLists() =
        coroutineScope {
            val popular = async(dispatcher) {
                apiService.popularMovieList()
            }
            val upcoming = async(dispatcher) {
                apiService.upcomingMovieList()
            }
            popular.await().results + upcoming.await().results
        }

    private suspend fun FlowCollector<Result<List<Movie>>>.handleMovieListExceptions(
        e: Throwable
    ) {
        emitNetworkError(
            e,
            MovieListException.NetworkException,
            MovieListException.ServerException,
            MovieListException.UnknownException(e)
        ) { (msg, code) ->
            MovieListException.InvalidException(msg, code)
        }
    }
}
