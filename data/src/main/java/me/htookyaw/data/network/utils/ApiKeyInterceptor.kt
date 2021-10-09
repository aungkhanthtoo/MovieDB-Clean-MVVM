package me.htookyaw.data.network.utils

import me.htookyaw.data.BuildConfig
import me.htookyaw.data.utils.NetworkHandler
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(private val networkHandler: NetworkHandler) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkHandler.isNetworkAvailable()) throw IOException()

        val request = chain.request()

        return chain.proceed(
            request.newBuilder()
                .url(addApiKeyParam(request.url()))
                .build()
        )
    }

    private fun addApiKeyParam(url: HttpUrl) = url.newBuilder()
        .addQueryParameter("api_key", BuildConfig.API_KEY)
        .build()
}
