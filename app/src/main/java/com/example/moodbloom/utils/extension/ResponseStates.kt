package com.example.moodbloom.utils.extension

sealed class ResponseStates<out R> {
    data class Success<out T>(var httpCode:Int,val data: T) : ResponseStates<T>()
    data class Failure(var httpCode:Int,val error: String) : ResponseStates<Nothing>()
    data class Unauthorized(var httpCode:Int,val error: String) : ResponseStates<Nothing>()
    object Loading : ResponseStates<Nothing>()
    object Idle : ResponseStates<Nothing>()
}

inline fun <T> ResponseStates<T>.onSuccess(action: (value: T) -> Unit): ResponseStates<T> {
    if (this is ResponseStates.Success) action(this.data)
    return this
}

inline fun <T> ResponseStates<T>.onFailure(action: (error: String) -> Unit): ResponseStates<T> {
    if (this is ResponseStates.Failure) action(error)
    return this
}


inline fun <T> ResponseStates<T>.onUnAuthorized(action: () -> Unit): ResponseStates<T> {
    if (this is ResponseStates.Unauthorized) action()
    return this
}
inline fun <T> ResponseStates<T>.onLoading(action: () -> Unit): ResponseStates<T> {
    if (this is ResponseStates.Loading) action()
    return this
}
