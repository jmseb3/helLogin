package com.wonddak.hellogin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.core.HelloginDefaultProvider
import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.github.GitHubProvider
import com.wonddak.hellogin.github.GithubLoginButton
import com.wonddak.hellogin.github.GithubLoginHelper
import com.wonddak.hellogin.github.network.model.GithubResult
import com.wonddak.hellogin.google.GoogleLoginButton
import com.wonddak.hellogin.google.GoogleResult
import com.wonddak.hellogin.google.getTokenString
import com.wonddak.hellogin.theme.AppTheme

internal class AnyTokenCallBack : TokenResultHandler {
    override fun onSuccess(token: Any) {
        if (token is GoogleResult) {
            println("Google onSuccess with $token >> ${token.getTokenString()}")
        } else if (token is GithubResult) {
            println("Github onSuccess with $token >> ${token.accessToken}")
        }
    }

    override fun onFail(error: Error?) {
        println("onFail with $error}")
    }
}


@Composable
internal fun App() = AppTheme {
    LaunchedEffect(true) {
        GithubLoginHelper.setOptionProvider(GitHubProvider())
        HelloginDefaultProvider.setAnyTokenHandler(AnyTokenCallBack())
    }
    var loginType by remember {
        mutableStateOf(LoginType.Google)
    }
    var type: ButtonType by remember {
        mutableStateOf(ButtonType.IconOnly)
    }
    var mode: ButtonTheme by remember {
        mutableStateOf(ButtonTheme.Light)
    }
    LaunchedEffect(loginType,type) {
        if (type is ButtonType.WithText) {
            type = ButtonType.WithText(loginType.text)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(30.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                LoginType.entries.forEach {
                    OutlinedButton(
                        onClick = {
                            loginType = it
                        },
                        modifier = Modifier.weight(1f),
                        enabled = loginType != it
                    ) {
                        Text(it.name)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                OutlinedButton(
                    onClick = {
                        type = ButtonType.IconOnly
                    },
                    modifier = Modifier.weight(1f),
                    enabled = type != ButtonType.IconOnly
                ) {
                    Text("Icon Only")
                }
                OutlinedButton(
                    onClick = {
                        type = ButtonType.WithText("")
                    },
                    modifier = Modifier.weight(1f),
                    enabled = type !is ButtonType.WithText
                ) {
                    Text("with Text")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                OutlinedButton(
                    onClick = {
                        mode = ButtonTheme.Light
                    },
                    modifier = Modifier.weight(1f),
                    enabled = mode != ButtonTheme.Light
                ) {
                    Text("Light")
                }
                OutlinedButton(
                    onClick = {
                        mode = ButtonTheme.Dark
                    },
                    modifier = Modifier.weight(1f),
                    enabled = mode != ButtonTheme.Dark
                ) {
                    Text("Dark")
                }
            }
        }

        when (loginType) {
            LoginType.Google -> {
                GoogleLoginButton(
                    type = type,
                    mode = mode,
                )
            }

            LoginType.GitHub -> {
                GithubLoginButton(
                    type = type,
                    mode = mode,
                )
            }
        }
    }
}

enum class LoginType(val text :String) {
    Google("Sign in with Google"),
    GitHub("Sign in with GitHub")
}