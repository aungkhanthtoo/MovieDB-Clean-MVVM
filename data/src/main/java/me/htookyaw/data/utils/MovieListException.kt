package me.htookyaw.data.utils

sealed class MovieListException(message: String) : Exception(message) {
    object NetworkException : MovieListException("No Internet Connection")
    class InvalidException(message: String, val code: Int) : MovieListException(message)
    object ServerException : MovieListException("Server Error")
    class UnknownException(val error: Throwable) : MovieListException("Something went wrong")
}
