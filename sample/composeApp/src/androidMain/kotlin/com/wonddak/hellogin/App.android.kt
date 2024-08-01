package com.wonddak.hellogin

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.wonddak.hellogin.core.Container
import com.wonddak.hellogin.core.LoginDefaultOptionProvider
import com.wonddak.hellogin.github.GithubLoginHelper
import com.wonddak.hellogin.github.GithubOptionProvider
import com.wonddak.hellogin.github.checkGithubAccessToken
import com.wonddak.hellogin.github.network.model.AuthRequestData
import com.wonddak.hellogin.github.network.model.ClientData
import com.wonddak.hellogin.github.network.model.CodeRequestData
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.OptionProviderAndroid
import com.wonddak.hellogin.google.setOptionProvider
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

class AppActivity : ComponentActivity(), OptionProviderAndroid {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LoginDefaultOptionProvider.setContainer(this)
        GoogleLoginHelper.setOptionProvider(this)
        enableEdgeToEdge()
        setContent { App() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        println(">>> onNewIntent")
        CoroutineScope(Dispatchers.Main).launch {
            intent.data?.getQueryParameter("code")?.let { code ->
                // 엑세스 토큰 받아와야함
                GithubLoginHelper.requestAuth(code)
            }
        }
    }

    override fun provideGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("336197927146-86mr7gssplbigvpq2nob1r0orpho8gvp.apps.googleusercontent.com")
            .build()
    }

}