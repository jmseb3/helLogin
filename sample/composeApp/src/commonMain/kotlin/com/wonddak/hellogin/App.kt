package com.wonddak.hellogin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
internal fun App() = AppTheme {
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .widthIn(min = 200.dp),
            onClick = {
                scope.launch {
                    GoogleLoginHelper.requestLogin()
                }
            },
            content = {
                Text(
                    "Google Login"
                )
            }
        )

    }
}

internal expect fun openUrl(url: String?)