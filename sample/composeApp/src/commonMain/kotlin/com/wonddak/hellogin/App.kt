package com.wonddak.hellogin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wonddak.hellogin.core.ButtonTheme
import com.wonddak.hellogin.core.ButtonType
import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.google.GoogleLoginButton
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleResult
import com.wonddak.hellogin.google.GoogleTokenHandler
import com.wonddak.hellogin.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
internal fun App(
    googleTokenHandler: GoogleTokenHandler
) = AppTheme {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        GoogleLoginButton(
            type = ButtonType.IconOnly,
            mode = ButtonTheme.Light,
            tokenResultHandler = googleTokenHandler
        )
        GoogleLoginButton(
            type = ButtonType.IconOnly,
            mode = ButtonTheme.Dark,
            tokenResultHandler = googleTokenHandler
        )
        GoogleLoginButton(
            mode = ButtonTheme.Light,
            tokenResultHandler = googleTokenHandler
        )
        GoogleLoginButton(
            mode = ButtonTheme.Dark,
            tokenResultHandler = googleTokenHandler
        )
    }
}