package com.wonddak.hellogin.core

interface LoginRequester {
    suspend fun requestLogin()
}