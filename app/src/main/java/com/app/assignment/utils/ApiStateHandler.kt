package com.app.assignment.utils

sealed class ApiStateHandler<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : ApiStateHandler<T>(data = data)
    class Failure<T>(message: String) : ApiStateHandler<T>(message = message)
    class Loading<T>() : ApiStateHandler<T>()
}