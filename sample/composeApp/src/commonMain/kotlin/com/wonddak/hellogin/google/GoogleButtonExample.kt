package com.wonddak.hellogin.google

import androidx.compose.runtime.Composable
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType

@Composable
internal fun GoogleButtonExample(
    callBack: GoogleResultCallBack = GoogleResultCallBack(),
) {
    GoogleLoginButton(
        type = ButtonType.IconOnly,
        mode = ButtonTheme.Light,
        tokenResultHandler = callBack
    )
    GoogleLoginButton(
        type = ButtonType.IconOnly,
        mode = ButtonTheme.Dark,
        tokenResultHandler = callBack
    )
    GoogleLoginButton(
        mode = ButtonTheme.Light,
        tokenResultHandler = callBack
    )
    GoogleLoginButton(
        mode = ButtonTheme.Dark,
        tokenResultHandler = callBack
    )
}