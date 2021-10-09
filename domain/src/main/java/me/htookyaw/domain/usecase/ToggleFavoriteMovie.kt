package me.htookyaw.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import me.htookyaw.domain.data.MovieRepository
import me.htookyaw.domain.usecase.base.IoDispatcher
import me.htookyaw.domain.usecase.base.UseCase
import javax.inject.Inject

class ToggleFavoriteMovie @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) : UseCase<Pair<Int, Boolean>, Unit>(coroutineDispatcher) {

    override suspend fun execute(parameters: Pair<Int, Boolean>) {
        val (id, favorite) = parameters
        repository.favoriteMovie(id, favorite)
    }
}
