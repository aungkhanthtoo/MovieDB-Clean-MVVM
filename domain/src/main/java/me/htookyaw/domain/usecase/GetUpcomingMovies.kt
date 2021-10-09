package me.htookyaw.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import me.htookyaw.domain.data.MovieRepository
import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.FlowUseCase
import me.htookyaw.domain.usecase.base.IoDispatcher
import me.htookyaw.domain.usecase.base.ResultFlow
import javax.inject.Inject

class GetUpcomingMovies @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Unit, List<Movie>>(
    dispatcher
) {

    override fun execute(parameters: Unit): ResultFlow<List<Movie>> {
        return repository.getAllMovies()
    }
}
