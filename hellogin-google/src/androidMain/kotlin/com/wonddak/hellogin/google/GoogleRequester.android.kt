package com.wonddak.hellogin.google

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.wonddak.hellogin.core.Error

actual typealias GoogleResult = GoogleIdTokenCredential
actual typealias Container = Activity


interface AndroidGoogleLoginOptionProvider : GoogleLoginProvider {
    override fun handleGoogleToken(token: GoogleResult)

    override fun handleFail(error: Error?)

    override fun provideContainer(): Container

    fun provideGoogleIdOption(): GetGoogleIdOption

    override suspend fun startGoogleLogin() {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(provideGoogleIdOption())
            .build()
        runCatching {
            val provideCredentialManager = CredentialManager.create(provideContainer().applicationContext)
            val result = provideCredentialManager.getCredential(
                request = request,
                context = provideContainer()
            )
            when (val credential = result.credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        try {
                            val googleIdTokenCredential = GoogleIdTokenCredential
                                .createFrom(credential.data)

                            handleGoogleToken(googleIdTokenCredential)
                        } catch (e: GoogleIdTokenParsingException) {
                            handleFail(e)
                        }
                    } else {
                        throw IllegalArgumentException("not google Login Type")
                    }
                }
                else -> {
                    throw IllegalArgumentException("not google Login Type")
                }
            }
        }.onFailure { e ->
            handleFail(e)
        }
    }
}