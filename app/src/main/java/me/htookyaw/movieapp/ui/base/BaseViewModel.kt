package me.htookyaw.movieapp.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.htookyaw.data.utils.Event

abstract class BaseViewModel<E> : ViewModel() {

    protected val _event = MutableLiveData<Event<E>>()
    val navigation: LiveData<Event<E>> get() = _event

    fun <T> MutableLiveData<Event<T>>.postEvent(e: T) {
        value = Event(e)
    }
}
