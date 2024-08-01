package com.wonddak.hellogin.github

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.github.network.model.GithubResult
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch

internal class GithubResultCallBack : GithubTokenHandler {
    override fun onSuccess(token: GithubResult) {
        super.onSuccess(token)
        println("Github onSuccess with $token >> ${token.accessToken}")

    }

    override fun onFail(error: Error?) {
        super.onFail(error)
        println("Github onFail with $error}")
    }
}

