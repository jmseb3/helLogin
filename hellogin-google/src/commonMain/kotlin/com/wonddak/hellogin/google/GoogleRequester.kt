package com.wonddak.hellogin.google

import com.wonddak.hellogin.core.LoginRequester
import com.wonddak.hellogin.core.TokenResultHandler


expect class GoogleResult

expect fun GoogleResult.getTokenResult(): String?

object GoogleLoginHelper : LoginRequester<GoogleResult> {

    private val provider: GoogleLoginProvider = GoogleLoginProvider()


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
    override suspend fun requestLogin(tokenHandler: TokenResultHandler<GoogleResult>) {
        require(optionProvider != null) { "optionProvider not init" }
        provider.startGoogleLogin(optionProvider!!,tokenHandler)
    }
}

/**
 * Class For Google Login
 */
internal expect class GoogleLoginProvider() {
    /**
     * Start Request For Google Login
     */
    suspend fun startGoogleLogin(
        optionProvider: GoogleOptionProvider,
        tokenHandler: TokenResultHandler<GoogleResult>
    )
}

/**
 * Option Provider Default Interface
 */
interface GoogleOptionProvider