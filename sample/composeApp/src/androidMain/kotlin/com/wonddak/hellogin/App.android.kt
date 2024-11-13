package com.wonddak.hellogin

import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.wonddak.hellogin.apple.AppleLoginHelper
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.github.parseResultForGithub
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.provider.MyAppleOptionProvider
import com.wonddak.hellogin.provider.MyGoogleOptionProvider
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

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HelloginContainerProvider.setContainer(this)
        GoogleLoginHelper.setOptionProvider(MyGoogleOptionProvider())
        AppleLoginHelper.setOptionProvider(MyAppleOptionProvider())
        enableEdgeToEdge()
        setContent { App() }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        CoroutineScope(Dispatchers.Main).launch {
            parseResultForGithub(intent)
        }
    }
}