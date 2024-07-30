package com.wonddak.hellogin

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.google.Container
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleResult
import com.wonddak.hellogin.google.GoogleTokenHandler
import com.wonddak.hellogin.google.OptionProviderAndroid

class AndroidApp : Application(){
    companion object {
        lateinit var INSTANCE: AndroidApp
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}

class AppActivity : ComponentActivity(), OptionProviderAndroid,GoogleTokenHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GoogleLoginHelper.setOptionProvider(this)
        enableEdgeToEdge()
        setContent { App(this@AppActivity) }
    }

    override fun provideGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("336197927146-86mr7gssplbigvpq2nob1r0orpho8gvp.apps.googleusercontent.com")
            .build()
    }

    override fun provideContainer(): Container {
        return this
    }

    override fun onSuccess(token: GoogleResult) {
        Log.d("JWH","google Login Success $token")
    }

    override fun onFail(error: Error?) {
        Log.e("JWH","fail google Login",error)
    }
}