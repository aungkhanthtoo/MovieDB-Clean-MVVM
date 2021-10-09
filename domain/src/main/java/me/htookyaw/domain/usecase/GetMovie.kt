package me.htookyaw.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import me.htookyaw.domain.data.MovieRepository
import me.htookyaw.domain.entitiy.Movie
import me.htookyaw.domain.usecase.base.FlowUseCase
import me.htookyaw.domain.usecase.base.IoDispatcher
import me.htookyaw.domain.usecase.base.ResultFlow
import javax.inject.Inject

class GetMovie @Inject constructor(
    private val repository: MovieRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Int, Movie>(
    dispatcher
) {

    override fun execute(parameters: Int): ResultFlow<Movie> {
        return repository.getMovie(parameters)
    }
}
