package com.wonddak.hellogin

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.google.AndroidGoogleLoginOptionProvider
import com.wonddak.hellogin.google.Container
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleResult

class AndroidApp : Application(){
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity(), AndroidGoogleLoginOptionProvider {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GoogleLoginHelper.setProvider(this)
        enableEdgeToEdge()
        setContent { App() }
    }

    override fun provideCredentialManager(): CredentialManager {
        return CredentialManager.create(this.applicationContext)
    }

    override fun provideGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("336197927146-86mr7gssplbigvpq2nob1r0orpho8gvp.apps.googleusercontent.com")
            .build()
    }

    override fun handleGoogleToken(token: GoogleResult) {
        Log.d("JWH","success : $token")
    }

    override fun handleFail(error: Error?) {
        error?.printStackTrace()
        Log.d("JWH","fail : $error")
    }

    override fun provideContainer(): Container {
        return this
    }
}

internal actual fun openUrl(url: String?) {
    val uri = url?.let { Uri.parse(it) } ?: return
    val intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = uri
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    AndroidApp.INSTANCE.startActivity(intent)
}