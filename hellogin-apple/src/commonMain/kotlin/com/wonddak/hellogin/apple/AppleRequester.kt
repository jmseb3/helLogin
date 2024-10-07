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

    /**
     * requestLogin
     */
    override suspend fun requestLogin(tokenHandler: TokenResultHandler<AppleResult>) {
        provider.startAppleLogin(
            requestScopes = listOf(
                AppleSignInRequestScope.Email
            ),
            tokenHandler
        )
    }
}

internal expect class AppleLoginProvider() {

    suspend fun startAppleLogin(
        requestScopes: List<AppleSignInRequestScope>,
        tokenHandler: TokenResultHandler<AppleResult>
    )
}