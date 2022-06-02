package com.bikotron.data.network

import android.content.Context

sealed class NetworkError {
    abstract fun getErrorMessage(context: Context): String
}

object UnknownNetworkError : NetworkError() {
    override fun getErrorMessage(context: Context): String {
        return "Unknown network error occurred"
    }
}

object ConnectionNetworkError : NetworkError() {
    override fun getErrorMessage(context: Context): String {
        return "Network is unavailable"
    }
}
