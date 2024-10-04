package com.wonddak.hellogin.github

import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.core.HelloginContainerProvider
import com.wonddak.hellogin.core.LoginRequester
import com.wonddak.hellogin.core.TokenResultHandler
import com.wonddak.hellogin.github.network.model.AuthRequestData
import com.wonddak.hellogin.github.network.model.CodeRequestData
import com.wonddak.hellogin.github.network.GithubClient
import com.wonddak.hellogin.github.network.model.ClientData
import com.wonddak.hellogin.github.network.model.GithubResult

object GithubLoginHelper : LoginRequester<GithubResult> {
    private var provider: GithubLoginProvider = GithubLoginProvider()
    private var client: GithubClient = GithubClient()
    private var optionProvider: GithubOptionProvider? = null

    /**
     * setOptionProvider
     */
    fun setOptionProvider(optionProvider: GithubOptionProvider) {
        this.optionProvider = optionProvider
    }

    private var savedTokenHandler : TokenResultHandler<GithubResult>? = null
    /**
     * requestLogin
     */
    override suspend fun requestLogin(tokenHandler: TokenResultHandler<GithubResult>) {
        require(optionProvider != null) { "optionProvider not init" }
        savedTokenHandler = tokenHandler
        provider.startGithubLogin(optionProvider!!, tokenHandler)
    }

    /**
     * request AuthToken
     */
    suspend fun requestAuth(code: String) {
        require(savedTokenHandler != null) { "TokenResultHandler not init" }
        client.requestAccessToken(AuthRequestData(optionProvider!!.provideClientData(), code))
            .onSuccess {
                savedTokenHandler!!.onSuccess(it)
            }.onFailure {
                savedTokenHandler!!.onFail(it as Error)
            }
    }
}

/**
 * Class For github Login
 */
internal expect class GithubLoginProvider() {
    /**
     * Start Request For Google Login
     */
    suspend fun startGithubLogin(
        optionProvider: GithubOptionProvider,
        tokenHandler: TokenResultHandler<GithubResult>
    )
}

/**
 * Option Provider Default Interface
 */
interface GithubOptionProvider {
    /**
     * request For Code
     *
     * **See Also:** [github Document](https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps#web-application-flow)
     */
    fun provideLoginId(): CodeRequestData

    /**
     * request For AccessToken
     *
     * **See Also:** [github Document](https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps#2-users-are-redirected-back-to-your-site-by-github)
     */
    fun provideClientData(): ClientData

    /**
     * CallBack Scheme
     * if scheme is hellogin
     * ```xml
     * //Android AndroidManifest.xml
     * <application
     *         android:name=".AndroidApp"
     *         android:icon="@android:drawable/ic_menu_compass"
     *         android:label="HeLLogin"
     *         <activity
     *             android:name=".AppActivity"
     *             android:configChanges="orientation|screenSize|screenLayout|keyboardHidden"
     *             android:launchMode="singleInstance"
     *             android:windowSoftInputMode="adjustPan"
     *             android:exported="true">
     *
     *                 <data
     *                     android:host="login"
     *                     android:scheme="hellogin"/>
     *             </intent-filter>
     *         </activity>
     *     </application>
     * ```
     */
    fun provideScheme(): String
}