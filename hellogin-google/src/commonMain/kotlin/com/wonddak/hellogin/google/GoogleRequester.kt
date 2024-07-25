package com.wonddak.hellogin.google

import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.core.LoginRequester


expect class GoogleResult
expect class Container

object GoogleLoginHelper : LoginRequester{

    private var provider : GoogleLoginProvider? = null

    fun setProvider(provider : GoogleLoginProvider) {
        this.provider = provider
    }

    override suspend fun requestLogin() {
        require(provider != null)
        provider!!.startGoogleLogin()
    }
}

interface GoogleLoginProvider {
    fun handleGoogleToken(token: GoogleResult)

    fun handleFail(error: Error?)

    fun provideContainer() : Container

    suspend fun startGoogleLogin()
}