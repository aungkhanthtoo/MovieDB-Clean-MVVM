package me.htookyaw.movieapp.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import me.htookyaw.domain.usecase.base.Result

fun ViewGroup.inflate(@LayoutRes id: Int) =
    LayoutInflater.from(context).inflate(id, this, false)

val <T> T.exhaustive: T
    get() = this

inline fun <T, E : Throwable> LiveData<Result<T>>.observeResult(
    owner: LifecycleOwner,
    crossinline onLoad: () -> Unit = {},
    crossinline onSuccess: (T) -> Unit = {},
    crossinline onError: (E) -> Unit
) {
    observe(owner) { result ->
        when (result) {
            is Result.Loading -> onLoad()
            is Result.Success -> onSuccess(result.data)
            is Result.Error<*> -> onError(result.exception as E)
        }.exhaustive
    }
}
