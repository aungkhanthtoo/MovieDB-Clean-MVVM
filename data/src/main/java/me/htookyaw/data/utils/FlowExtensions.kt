package me.htookyaw.data.utils

import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.mapLatest
import me.htookyaw.data.network.response.InvalidResponse
import me.htookyaw.domain.usecase.base.Result
import me.htookyaw.domain.usecase.base.ResultFlow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

suspend fun <T> FlowCollector<Result<T>>.emitResult(data: T) {
    emit(Result.Success(data))
}

suspend fun <E : Exception> FlowCollector<Result.Error<out E>>.emitNetworkError(
    original: Throwable,
    networkException: E,
    serverException: E,
    unknownException: E,
    httpException: (InvalidResponse) -> E
) {
    when (original) {
        is IOException -> emit(Result.Error(networkException))
        is HttpException -> when (original.code()) {
            401, 404 -> {
                val errorResponse = Gson().fromJson(
                    original.response()?.errorBody()?.charStream(),
                    InvalidResponse::class.java
                )
                emit(
                    Result.Error(
                        httpException(errorResponse)
                    )
                )
            }
            else -> emit(Result.Error(serverException))
        }
        else -> {
            emit(Result.Error(unknownException))
        }
    }
    Timber.e(original)
}

@ExperimentalCoroutinesApi
fun <T, R> ResultFlow<T>.mapSuccess(mapper: suspend (T) -> R): ResultFlow<R> =
    mapLatest {
        when (it) {
            is Result.Success -> Result.Success(mapper(it.data))
            is Result.Error<*> -> it
            Result.Loading -> Result.Loading
        }
    }
