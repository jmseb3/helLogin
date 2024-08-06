package com.wonddak.hellogin.google

import com.wonddak.hellogin.core.LoginRequester


expect class GoogleResult

expect fun GoogleResult.getTokenString(): String?

object GoogleLoginHelper : LoginRequester<GoogleResult> {

    private var provider: GoogleLoginProvider = GoogleLoginProvider()


    private var optionProvider: GoogleOptionProvider? = null
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
        require(optionProvider != null) { "optionProvider not init" }
        provider.startGoogleLogin(optionProvider!!)
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
        optionProvider: GoogleOptionProvider,
    )
}

/**
 * Option Provider Default Interface
 */
interface GoogleOptionProvider