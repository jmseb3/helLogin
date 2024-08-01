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
import com.wonddak.hellogin.github.GitHubProvider
import com.wonddak.hellogin.github.GithubButtonExample
import com.wonddak.hellogin.github.GithubLoginHelper
import com.wonddak.hellogin.github.GithubOptionProvider
import com.wonddak.hellogin.github.GithubTokenHandler
import com.wonddak.hellogin.github.network.model.ClientData
import com.wonddak.hellogin.github.network.model.CodeRequestData
import com.wonddak.hellogin.github.network.model.GithubResult
import com.wonddak.hellogin.google.GoogleButtonExample
import com.wonddak.hellogin.google.GoogleLoginButton
import com.wonddak.hellogin.google.GoogleLoginHelper
import com.wonddak.hellogin.google.GoogleResult
import com.wonddak.hellogin.google.GoogleTokenHandler
import com.wonddak.hellogin.google.getTokenString
import com.wonddak.hellogin.theme.AppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
internal fun App() = AppTheme {
    LaunchedEffect(true) {
        GithubLoginHelper.setOptionProvider(GitHubProvider())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        GoogleButtonExample()
        GithubButtonExample()
    }
}