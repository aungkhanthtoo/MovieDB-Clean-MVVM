package me.htookyaw.data.network

import me.htookyaw.data.network.response.MovieListResponse
import me.htookyaw.data.network.utils.EndPoints
import retrofit2.http.GET

interface MovieApi {

    @GET(EndPoints.UPCOMING_MOVIES)
    suspend fun upcomingMovieList(): MovieListResponse

    @GET(EndPoints.POPULAR_MOVIES)
    suspend fun popularMovieList(): MovieListResponse
}
