package me.htookyaw.movieapp.ui.movielist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.htookyaw.data.utils.mapSuccess
import me.htookyaw.domain.usecase.GetUpcomingMovies
import me.htookyaw.domain.usecase.RefreshMovies
import me.htookyaw.domain.usecase.ToggleFavoriteMovie
import me.htookyaw.domain.usecase.base.Result
import me.htookyaw.movieapp.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getPopularUpcomingMovies: GetUpcomingMovies,
    private val refreshMovies: RefreshMovies,
    private val favoriteMovie: ToggleFavoriteMovie
) : BaseViewModel<MovieListEvent>() {

    @ExperimentalCoroutinesApi
    val movies = getPopularUpcomingMovies(Unit)
        .onStart { emit(Result.Loading) }
        .mapSuccess { data -> data.map { MovieUI.of(it) } }
        .asLiveData()

    private val _refreshing = MutableLiveData<Unit>()
    val refreshing = _refreshing.switchMap {
        refreshMovies(it)
            .onStart { emit(Result.Loading) }
            .asLiveData()
    }

    private var favoriteJob: Job? = null

    fun onRefreshed() {
        _refreshing.value = Unit
    }

    fun onFavoriteClicked(movie: MovieUI) {
        if (favoriteJob?.isActive == true) {
            favoriteJob?.cancel()
        }
        favoriteJob = viewModelScope.launch {
            favoriteMovie(movie.id to !movie.favorite)
        }
    }

    fun onItemClick(data: MovieUI) {
        _event.postEvent(MovieListEvent.NavigateDetail(data))
    }
}

sealed class MovieListEvent {
    class NavigateDetail(val data: MovieUI) : MovieListEvent()
}
