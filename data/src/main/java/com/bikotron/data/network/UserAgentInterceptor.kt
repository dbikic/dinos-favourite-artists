package com.bikotron.data.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class UserAgentInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithUserAgent = originalRequest.newBuilder()
            .header("User-Agent", "DinosFavouriteAlbums/0.1.0 +https://github.com/dbikic")
            .build()
        return chain.proceed(requestWithUserAgent)
    }
}
