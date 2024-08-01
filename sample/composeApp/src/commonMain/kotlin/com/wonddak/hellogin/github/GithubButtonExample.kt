package com.wonddak.hellogin.github

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
internal fun GithubButtonExample(
    callBack: GithubResultCallBack = GithubResultCallBack(),
) {
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                GithubLoginHelper.requestLoginWithTokenHandler(callBack)
            }
        }
    ) {
        Text("Github")
    }
}