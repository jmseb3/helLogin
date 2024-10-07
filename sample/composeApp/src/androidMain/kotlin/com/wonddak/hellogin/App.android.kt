package com.wonddak.hellogin

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.wonddak.hellogin.apple.AppleOptionProviderAndroid
import com.wonddak.hellogin.apple.AppleSignInRequestScope
import com.wonddak.hellogin.apple.parseResultForApple
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.github.parseResultForGithub
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleOptionProviderAndroid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AndroidApp : Application() {
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity(), GoogleOptionProviderAndroid ,AppleOptionProviderAndroid {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HelloginContainerProvider.setContainer(this)
        GoogleLoginHelper.setOptionProvider(this)
        enableEdgeToEdge()
        setContent { App() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        println(">>> onNewIntent")
        CoroutineScope(Dispatchers.Main).launch {

            parseResultForApple(intent) {

            }
            parseResultForGithub(intent)
        }
    }

    override fun provideGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("336197927146-86mr7gssplbigvpq2nob1r0orpho8gvp.apps.googleusercontent.com")
            .build()
    }

    override val mClientId: String
        get() = TODO("Not yet implemented")

    override val mRedirectUrl: String
        get() = "https://hellogin-9db75.firebaseapp.com/__/auth/handler"

    override val requestScope: AppleSignInRequestScope
        get() = AppleSignInRequestScope.FullNameAndEmail
}