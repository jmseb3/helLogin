package com.wonddak.hellogin.google

import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.core.TokenResultHandler

actual typealias GoogleResult = GoogleIdTokenCredential

actual fun GoogleResult.getTokenResult(): String? {
    return this.idToken
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
        if (optionProvider is GoogleOptionProviderAndroid) {
            val container = HelloginContainerProvider.getContainer()

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
                            throw IllegalArgumentException("This is not CustomCredential But type is not \"TYPE_GOOGLE_ID_TOKEN_CREDENTIAL\"")
                        }
                    }

                    else -> {
                        throw IllegalArgumentException("This is not CustomCredential")
                    }
                }
            }.onFailure { e ->
                tokenHandler.onFail(e)
            }
        } else {
            throw IllegalStateException("on Android need \"GoogleOptionProviderAndroid\" your type is ${optionProvider.javaClass.simpleName} ")
        }
    }
}

interface GoogleOptionProviderAndroid : GoogleOptionProvider {
    fun provideGoogleIdOption(): GetGoogleIdOption
}
