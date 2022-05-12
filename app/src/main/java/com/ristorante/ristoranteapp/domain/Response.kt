package com.ristorante.ristoranteapp.domain

sealed class Response<T : Any> {
    data class Success<T : Any>(val data: T) : Response<T>()
    data class Failure<T : Any>(val data: Throwable) : Response<T>()
}
