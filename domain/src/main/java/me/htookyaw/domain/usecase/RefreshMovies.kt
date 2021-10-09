package me.htookyaw.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import me.htookyaw.domain.data.MovieRepository
import me.htookyaw.domain.usecase.base.FlowUseCase
import me.htookyaw.domain.usecase.base.IoDispatcher
import me.htookyaw.domain.usecase.base.ResultFlow
import javax.inject.Inject

class RefreshMovies @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameters: Unit): ResultFlow<Unit> {
        return repository.refreshMovies()
    }
}
