package com.wonddak.hellogin.github

import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.wonddak.hellogin.core.HelloginDefaultProvider
import com.wonddak.hellogin.github.network.model.ClientData

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

/**
 * for android check onNewIntent ..
 */
suspend fun Intent.checkGithubAccessToken(clientData: ClientData) {

}