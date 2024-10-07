@file:OptIn(ExperimentalForeignApi::class)

package com.wonddak.hellogin.google

import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.core.TokenResultHandler
import kotlinx.cinterop.ExperimentalForeignApi


actual typealias GoogleResult = GIDSignInResult

actual fun GoogleResult.getTokenResult(): String? {
    return this.user().idToken()?.tokenString()
}

/**
 * Class For Google Login
 */
internal actual class GoogleLoginProvider actual constructor() {
    /**
     * Start Request For Google Login
     */
    actual suspend fun startGoogleLogin(
        optionProvider: GoogleOptionProvider,
        tokenHandler: TokenResultHandler<GoogleResult>
    ) {
        val container = HelloginContainerProvider.getContainer()
        GIDSignIn.sharedInstance()
            .signInWithPresentingViewController(presentingViewController = container) { result, error ->
                if (result == null || error != null) {
                    tokenHandler.onFail(error)
                    return@signInWithPresentingViewController
                }
                tokenHandler.onSuccess(result)
            }
    }
}

fun GoogleLoginHelper.setEmptyOption() {
    this.setOptionProvider(object : GoogleOptionProvider {
        
    })
}