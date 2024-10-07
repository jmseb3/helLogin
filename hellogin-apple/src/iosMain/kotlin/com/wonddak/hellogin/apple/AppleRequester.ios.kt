package com.wonddak.hellogin.apple

import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.core.makeMsgError
import kotlinx.cinterop.BetaInteropApi
import platform.AuthenticationServices.ASAuthorization
import platform.AuthenticationServices.ASAuthorizationAppleIDCredential
import platform.AuthenticationServices.ASAuthorizationAppleIDProvider
import platform.AuthenticationServices.ASAuthorizationController
import platform.AuthenticationServices.ASAuthorizationControllerDelegateProtocol
import platform.AuthenticationServices.ASAuthorizationControllerPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASAuthorizationScopeEmail
import platform.AuthenticationServices.ASAuthorizationScopeFullName
import platform.AuthenticationServices.ASPresentationAnchor
import platform.Foundation.NSError
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.UIKit.UIApplication
import platform.darwin.NSObject

actual typealias AppleCred = ASAuthorizationAppleIDCredential


private var currentNonce: String? = null

internal actual class AppleLoginProvider actual constructor() {

    actual suspend fun startAppleLogin(
        requestScopes: List<AppleSignInRequestScope>,
        tokenHandler: TokenResultHandler<AppleResult>
    ) {
        val nonce = AppleLoginUtil.randomNonceString()
        currentNonce = nonce
        val appleIDProvider = ASAuthorizationAppleIDProvider()
        val request = appleIDProvider.createRequest()
        request.requestedScopes = requestScopes.map {
            when (it) {
                AppleSignInRequestScope.Email -> ASAuthorizationScopeEmail
                AppleSignInRequestScope.FullName -> ASAuthorizationScopeFullName
            }
        }
        request.nonce = AppleLoginUtil.sha256(nonce)
        val requests = listOf(request)

        val authorizationController =
            ASAuthorizationController(authorizationRequests = requests)

        val delegate = ASAuthorizationControllerDelegate(
            tokenHandler = tokenHandler
        )

        val presentationContextProvider = PresentProvider()

        authorizationController.setDelegate(delegate)
        authorizationController.setPresentationContextProvider(presentationContextProvider)

        authorizationController.performRequests()
    }
}

private class PresentProvider() : ASAuthorizationControllerPresentationContextProvidingProtocol, NSObject() {
    override fun presentationAnchorForAuthorizationController(controller: ASAuthorizationController): ASPresentationAnchor {
        val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        return rootViewController?.view?.window
    }
}

private class ASAuthorizationControllerDelegate(
    private val tokenHandler: TokenResultHandler<AppleResult>
) : ASAuthorizationControllerDelegateProtocol, NSObject() {

    override fun authorizationController(
        controller: ASAuthorizationController,
        didCompleteWithError: NSError
    ) {
        // Handle error.
        tokenHandler.onFail(didCompleteWithError)
    }

    @OptIn(BetaInteropApi::class)
    override fun authorizationController(
        controller: ASAuthorizationController,
        didCompleteWithAuthorization: ASAuthorization
    ) {
        println("AppleSignIn: authorizationController success function is called")
        val authorization = didCompleteWithAuthorization

        val appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential
        if (currentNonce == null) {
            tokenHandler.onFail(
                makeMsgError("Invalid state: A login callback was received, but no login request was sent.")
            )
            return
        }
        val appleIdToken = appleIDCredential?.identityToken
        if (appleIdToken == null) {
            tokenHandler.onFail(
                makeMsgError("Unable to fetch identity token")
            )
            return
        }
        val idTokenString = NSString.create(appleIdToken, NSUTF8StringEncoding)?.toString()
        if (idTokenString == null) {
            tokenHandler.onFail(
                makeMsgError("Unable to serialize token string from data")
            )
            return
        }
        tokenHandler.onSuccess(
            AppleResult(
                idTokenString,
                currentNonce,
                appleIDCredential
            )
        )
    }
}

