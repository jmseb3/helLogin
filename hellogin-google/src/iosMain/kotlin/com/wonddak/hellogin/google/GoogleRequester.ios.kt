@file:OptIn(ExperimentalForeignApi::class)

package com.wonddak.hellogin.google

import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import com.wonddak.hellogin.core.HelloginDefaultProvider
import kotlinx.cinterop.ExperimentalForeignApi


actual typealias GoogleResult = GIDSignInResult

actual fun GoogleResult.getTokenString(): String? {
    return this.user().idToken()?.tokenString
}

actual class GoogleLoginProvider actual constructor() {

    /**
     * Start Request For Google Login
     */
    actual suspend fun startGoogleLogin(
        optionProvider: GoogleOptionProvider,
    ) {
        val container = HelloginDefaultProvider.getContainer()
        val tokenHandler = HelloginDefaultProvider.getAnyTokenHandler()
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
