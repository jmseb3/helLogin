package com.wonddak.hellogin.github

import androidx.compose.runtime.Composable
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType

@Composable
internal fun GithubButtonExample(
    callBack: GithubResultCallBack = GithubResultCallBack(),
) {
    GithubLoginButton(
        type = ButtonType.IconOnly,
        mode = ButtonTheme.Light,
        tokenResultHandler = callBack
    )
    GithubLoginButton(
        type = ButtonType.IconOnly,
        mode = ButtonTheme.Dark,
        tokenResultHandler = callBack
    )
    GithubLoginButton(
        mode = ButtonTheme.Light,
        tokenResultHandler = callBack
    )
    GithubLoginButton(
        mode = ButtonTheme.Dark,
        tokenResultHandler = callBack
    )
}