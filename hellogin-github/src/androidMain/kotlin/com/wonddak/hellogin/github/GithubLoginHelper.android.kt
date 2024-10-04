package com.wonddak.hellogin.github

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.github.network.model.GithubResult

/**
 * Class For github Login
 */
internal actual class GithubLoginProvider actual constructor() {
    /**
     * Start Request For Google Login
     */
    actual suspend fun startGithubLogin(
        optionProvider: GithubOptionProvider,
        tokenHandler: TokenResultHandler<GithubResult>
    ) {
        val container = HelloginContainerProvider.getContainer()
        val url = GithubPath.getLoginPage(optionProvider.provideLoginId())

        val intent = CustomTabsIntent.Builder()
            .build()

        intent.launchUrl(container, Uri.parse(url))
    }
}