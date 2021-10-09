package me.htookyaw.data.network.utils

sealed class NetworkException : Exception() {
    class ApiException(override val message: String) : NetworkException()

    object TimeOutException : NetworkException()

    class DefaultException(override val message: String) : NetworkException()

    class NoConnectivityException : NetworkException() {
        override val message: String?
            get() = "No network available, please check your WiFi or Data connection"
    }

    class NoInternetException : NetworkException() {
        override val message: String?
            get() = "No internet available, please check your connected WIFi or Data"
    }
}
