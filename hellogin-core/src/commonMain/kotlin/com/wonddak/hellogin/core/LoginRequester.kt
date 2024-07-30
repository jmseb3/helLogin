package com.wonddak.hellogin.core

/**
 * Base of Login Interface
 */
interface LoginRequester {
    suspend fun requestLogin()
}

/**
 * Result Handler For T
 * T is Token Per Auth
 */
interface TokenResultHandler<T> {
    fun onSuccess(token:T)
    fun onFail(error: Error?)
}