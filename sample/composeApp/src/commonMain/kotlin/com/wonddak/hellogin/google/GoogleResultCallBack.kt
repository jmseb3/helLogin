package com.wonddak.hellogin.google

import androidx.compose.runtime.Composable
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType
import com.wonddak.hellogin.core.Error

internal class GoogleResultCallBack : GoogleTokenHandler {
    override fun onSuccess(token: GoogleResult) {
        super.onSuccess(token)
        println("Google onSuccess with $token >> ${token.getTokenString()}")
    }

    override fun onFail(error: Error?) {
        super.onFail(error)
        println("Google onFail with $error}")
    }
}
