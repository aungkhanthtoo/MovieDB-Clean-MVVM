package me.htookyaw.movieapp.ui.moviedetail

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import me.htookyaw.data.utils.mapSuccess
import me.htookyaw.domain.usecase.GetMovie
import me.htookyaw.domain.usecase.ToggleFavoriteMovie
import me.htookyaw.domain.usecase.base.Result
import me.htookyaw.domain.usecase.base.data
import me.htookyaw.movieapp.ui.base.BaseViewModel
import me.htookyaw.movieapp.ui.movielist.MovieUI
import me.htookyaw.movieapp.ui.movielist.of
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    getMovie: GetMovie,
    private val favoriteMovie: ToggleFavoriteMovie,
    saveState: SavedStateHandle
) : BaseViewModel<Nothing>() {

    val movie: LiveData<Result<MovieUI>> = saveState.getLiveData<Int>("movie_id").switchMap { id ->
        getMovie(id)
            .onStart { emit(Result.Loading) }
            .mapSuccess { MovieUI.of(it) }
            .asLiveData()
    }

    private var favoriteJob: Job? = null

    fun onFavoriteClicked() {
        if (favoriteJob?.isActive == true) {
            favoriteJob?.cancel()
        }
        favoriteJob = viewModelScope.launch {
            movie.value?.data?.let { movie ->
                favoriteMovie(movie.id to !movie.favorite)
            }
        }
    }
}
