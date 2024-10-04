package com.wonddak.hellogin.github

import com.wonddak.hellogin.core.Container
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.github.network.model.GithubResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.AuthenticationServices.ASPresentationAnchor
import platform.AuthenticationServices.ASWebAuthenticationPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASWebAuthenticationSession
import platform.Foundation.NSURL
import platform.darwin.NSObject

/**
 * Class For github Login
 */
internal actual class GithubLoginProvider actual constructor() {

    private inner class WebAuthSessionPresentationContextProvider(
        private val container: Container,
    ) : NSObject(), ASWebAuthenticationPresentationContextProvidingProtocol {
        override fun presentationAnchorForWebAuthenticationSession(session: ASWebAuthenticationSession): ASPresentationAnchor {
            return container.view.window ?: ASPresentationAnchor()
        }
    }

    private val scope = CoroutineScope(Dispatchers.Main)
    /**
     * Start Request For Google Login
     */
    actual suspend fun startGithubLogin(
        optionProvider: GithubOptionProvider,
        tokenHandler: TokenResultHandler<GithubResult>
    ) {
        val container = HelloginContainerProvider.getContainer()
        val urlString = GithubPath.getLoginPage(optionProvider.provideLoginId())
        val url: NSURL = NSURL.URLWithString(urlString)!!
        val webAuthSession: ASWebAuthenticationSession = ASWebAuthenticationSession(
            uRL = url,
            callbackURLScheme = optionProvider.provideScheme()
        ) { callback, error ->
            if (error != null) {
                return@ASWebAuthenticationSession
            }
            if (callback == null) {
                return@ASWebAuthenticationSession
            }
            run {
                callback.query!!.split("&").forEach {
                    val (key, value) = it.split("=")
                    if (key == "code") {
                        scope.launch {
                            GithubLoginHelper.requestAuth(value)
                        }
                        return@run
                    }
                }
            }
        }
        webAuthSession.presentationContextProvider =
            WebAuthSessionPresentationContextProvider(container)
        webAuthSession.start()
    }
}