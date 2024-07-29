package com.wonddak.hellogin.core

/**
 * Base of Login Interface
 */
interface LoginRequester {
    suspend fun requestLogin()
}