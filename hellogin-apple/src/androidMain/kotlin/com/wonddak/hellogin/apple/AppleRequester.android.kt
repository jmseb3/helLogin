package com.wonddak.hellogin.apple

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.core.TokenResultHandler
import org.jetbrains.annotations.ApiStatus.Experimental
import java.util.UUID

actual typealias AppleCred = String

private fun AppleSignInRequestScope.convert(): String {
    return when (this) {
        AppleSignInRequestScope.Email -> "email"
        AppleSignInRequestScope.FullName -> "name"
        AppleSignInRequestScope.FullNameAndEmail -> "name%20email"
    }
}

private var savedTokenHandler : TokenResultHandler<AppleResult>? = null

internal actual class AppleLoginProvider actual constructor() {
    private val mAuthEndpoint = "https://appleid.apple.com/auth/authorize"
    private val mResponseType = "code%20id_token"
    private val mResponseMode = "form_post"
    private val mState = UUID.randomUUID().toString()

    actual suspend fun startAppleLogin(
        optionProvider: AppleOptionProvider,
        tokenHandler: TokenResultHandler<AppleResult>
    ) {
        if (optionProvider is AppleOptionProviderAndroid) {
            savedTokenHandler = tokenHandler
            val customTabsIntent = CustomTabsIntent.Builder().build()
            customTabsIntent.launchUrl(
                HelloginContainerProvider.getContainer(),
                makeUri(optionProvider)
            )
        }
    }

    private fun makeUri(
        optionProvider: AppleOptionProviderAndroid
    ): Uri {
        val url = StringBuilder()
        url.append(mAuthEndpoint)

        url.append("?response_type=")
        url.append(mResponseType)

        url.append("&response_mode=")
        url.append(mResponseMode)

        url.append("&client_id=")
        url.append(optionProvider.mClientId)

        url.append("&scope=")
        url.append(optionProvider.requestScope.convert())

        url.append("&state=")
        url.append(mState)

        url.append("&redirect_uri=")
        url.append(optionProvider.mRedirectUrl)

        return Uri.parse(url.toString())
    }
}

/**
 * Option Provider Default Interface
 */
interface AppleOptionProviderAndroid : AppleOptionProvider {
    /**
     * The identifier (App ID or Services ID) for your app. The identifier must not include your Team ID, to help prevent the possibility of exposing sensitive data to the end user.
     */
    val mClientId: String

    /**
     * The destination URI associated to your app, to which the authorization redirects. The URI must use the HTTPS protocool, include a domain name, can’t be an IP address or localhost, and must not contain a fragment identifer (#). Visit Configure Sign in with Apple for the web for more information.
     *
     * **See Also:**  [Sign in with Apple for the web](https://developer.apple.com/help/account/configure-app-capabilities/configure-sign-in-with-apple-for-the-web)
     */
    val mRedirectUrl: String
}

