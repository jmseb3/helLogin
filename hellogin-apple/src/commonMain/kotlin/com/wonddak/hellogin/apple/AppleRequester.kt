package com.wonddak.hellogin.apple

import com.wonddak.hellogin.core.LoginRequester
import com.wonddak.hellogin.core.TokenResultHandler
import kotlin.native.concurrent.ThreadLocal

data class AppleResult(
    val tokenString : String,
    val nonce :String?,
    val cred : AppleCred
)


expect class AppleCred

@ThreadLocal
object AppleLoginHelper : LoginRequester<AppleResult> {

    private val provider: AppleLoginProvider = AppleLoginProvider()

    private var optionProvider: AppleOptionProvider? = null

    /**
     * setOptionProvider
     * @see[AppleOptionProvider]
     */
    fun setOptionProvider(optionProvider: AppleOptionProvider) {
        this.optionProvider = optionProvider
    }
    /**
     * requestLogin
     */
    override suspend fun requestLogin(tokenHandler: TokenResultHandler<AppleResult>) {
        require(optionProvider != null) { "optionProvider not init" }
        provider.startAppleLogin(
            optionProvider!!,
            tokenHandler
        )
    }
}

internal expect class AppleLoginProvider() {

    suspend fun startAppleLogin(
        optionProvider: AppleOptionProvider,
        tokenHandler: TokenResultHandler<AppleResult>
    )
}

/**
 * Option Provider Default Interface
 */
interface AppleOptionProvider {
    val requestScope : AppleSignInRequestScope
}