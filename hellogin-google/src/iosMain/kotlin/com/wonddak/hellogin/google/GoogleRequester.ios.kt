package com.wonddak.hellogin.google

import cocoapods.GoogleSignIn.GIDSignIn
import cocoapods.GoogleSignIn.GIDSignInResult
import com.wonddak.hellogin.core.Error
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene


@OptIn(ExperimentalForeignApi::class)
actual typealias GoogleResult = GIDSignInResult
actual typealias Container = UIViewController


actual class GoogleLoginProvider actual constructor() {

    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun startGoogleLogin(
        tokenHandler: GoogleTokenHandler,
        optionProvider: GoogleOptionProvider,
    ) {
        GIDSignIn.sharedInstance()
            .signInWithPresentingViewController(presentingViewController = optionProvider.provideContainer()) { result, error ->
                if (result == null || error != null) {
                    tokenHandler.onFail(error)
                    return@signInWithPresentingViewController
                }
                tokenHandler.onSuccess(result)
            }
    }
}

/**
 * Ios OptionProvider Default
 * This is Provide Top View Controller
 */
class OptionProviderIos() : GoogleOptionProvider {
    override fun provideContainer(): Container {
        val presentingViewController = ((UIApplication.sharedApplication().connectedScenes()
            .first() as? UIWindowScene)?.windows() as List<UIWindow?>).first()?.rootViewController()!!
        return presentingViewController
    }
}

/**
 * @see[GoogleOptionProvider]
 * @see[OptionProviderIos]
 */
fun GoogleLoginHelper.setDefaultOptionProvider() {
    this.setOptionProvider(OptionProviderIos())
}