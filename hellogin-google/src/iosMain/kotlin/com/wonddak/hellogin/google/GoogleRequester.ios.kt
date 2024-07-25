package com.wonddak.hellogin.google

import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import com.wonddak.hellogin.core.Error
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIViewController


@OptIn(ExperimentalForeignApi::class)
actual typealias GoogleResult = GIDSignInResult
actual typealias Container = UIViewController

@OptIn(ExperimentalForeignApi::class)
interface IOSGoogleLoginOptionProvider : GoogleLoginProvider {
    override fun handleGoogleToken(token: GoogleResult)

    override fun handleFail(error: Error?)

    override fun provideContainer(): Container

    override suspend fun startGoogleLogin(
    ) {
        GIDSignIn.sharedInstance()
            .signInWithPresentingViewController(presentingViewController = provideContainer()) { result, error ->
                if (result == null || error != null) {
                    handleFail(error)
                    return@signInWithPresentingViewController
                }
                handleGoogleToken(result)
            }
    }
}