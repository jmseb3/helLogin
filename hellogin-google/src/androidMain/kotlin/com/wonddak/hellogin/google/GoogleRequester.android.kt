package com.wonddak.hellogin.google

import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.wonddak.hellogin.core.LoginDefaultOptionProvider

actual typealias GoogleResult = GoogleIdTokenCredential

actual fun GoogleResult.getTokenString(): String? {
    return this.idToken
}

/**
 * set OptionProviderAndroid
 */
fun GoogleLoginHelper.setOptionProvider(android: OptionProviderAndroid) {
    this.setOptionProvider(android)
}

actual class GoogleLoginProvider actual constructor() {
    /**
     * Start Request For Google Login
     */
    actual suspend fun startGoogleLogin(
        tokenHandler: GoogleTokenHandler,
        optionProvider: GoogleOptionProvider,
    ) {
        optionProvider as OptionProviderAndroid
        val container = LoginDefaultOptionProvider.getContainer()
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(optionProvider.provideGoogleIdOption())
            .build()
        runCatching {
            val provideCredentialManager =
                CredentialManager.create(container.applicationContext)
            val result = provideCredentialManager.getCredential(
                request = request,
                context = container
            )
            when (val credential = result.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)

                            tokenHandler.onSuccess(googleIdTokenCredential)
                        } catch (e: GoogleIdTokenParsingException) {
                            tokenHandler.onFail(e)
                        }
                    } else {
                        throw IllegalArgumentException("This is not CustomCredential But type is not TYPE_GOOGLE_ID_TOKEN_CREDENTIAL")
                    }
                }

                else -> {
                    throw IllegalArgumentException("This is not CustomCredential")
                }
            }
        }.onFailure { e ->
            tokenHandler.onFail(e)
        }
    }

}

interface OptionProviderAndroid : GoogleOptionProvider {
    fun provideGoogleIdOption(): GetGoogleIdOption
}