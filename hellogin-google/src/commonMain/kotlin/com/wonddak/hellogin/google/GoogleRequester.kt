package com.wonddak.hellogin.google

import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.core.LoginRequester
import com.wonddak.hellogin.core.TokenResultHandler


expect class GoogleResult

expect fun GoogleResult.getTokenString(): String?
expect class Container

object GoogleLoginHelper : LoginRequester {

    private var provider: GoogleLoginProvider = GoogleLoginProvider()

    private var tokenHandler: GoogleTokenHandler? = null
    private var optionProvider: GoogleOptionProvider? = null

    /**
     * setTokenHandler
     * @see[GoogleTokenHandler]
     */
    fun setTokenHandler(tokenHandler: GoogleTokenHandler) {
        this.tokenHandler = tokenHandler
    }

    /**
     * setOptionProvider
     * @see[GoogleOptionProvider]
     */
    fun setOptionProvider(optionProvider: GoogleOptionProvider) {
        this.optionProvider = optionProvider
    }

    /**
     * requestLogin
     */
    override suspend fun requestLogin() {
        require(tokenHandler != null) { "tokenHandler not init" }
        require(optionProvider != null) { "optionProvider not init" }
        provider.startGoogleLogin(tokenHandler!!, optionProvider!!)
    }

    suspend fun requestLoginWithTokenHandler(tokenHandler: GoogleTokenHandler) {
        setTokenHandler(tokenHandler)
        this.requestLogin()
    }
}

/**
 * expect Class For Google Login
 */
expect class GoogleLoginProvider() {
    /**
     * Start Request For Google Login
     */
    suspend fun startGoogleLogin(
        tokenHandler: GoogleTokenHandler, optionProvider: GoogleOptionProvider,
    )
}

/**
 * Token Handler For GoogleResult
 * @see[GoogleResult]
 * @see[TokenResultHandler]
 */
interface GoogleTokenHandler : TokenResultHandler<GoogleResult> {
    override fun onSuccess(token: GoogleResult) {

    }

    override fun onFail(error: Error?) {

    }
}

/**
 * Option Provider Default Interface
 */
interface GoogleOptionProvider {
    fun provideContainer(): Container
}