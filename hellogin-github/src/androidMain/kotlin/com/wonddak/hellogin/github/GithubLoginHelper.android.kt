package com.wonddak.hellogin.github

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.wonddak.hellogin.core.HelloginDefaultProvider

/**
 * expect Class For github Login
 */
internal actual class GithubLoginProvider actual constructor() {
    /**
     * Start Request For Google Login
     */
    actual suspend fun startGithubLogin(optionProvider: GithubOptionProvider) {
        val container = HelloginDefaultProvider.getContainer()
        val url = GithubPath.getLoginPage(optionProvider.provideLoginId())

        val intent = CustomTabsIntent.Builder()
            .build()

        intent.launchUrl(container, Uri.parse(url))
    }
}