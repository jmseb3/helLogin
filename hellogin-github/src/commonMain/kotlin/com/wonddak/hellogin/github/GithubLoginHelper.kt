package com.wonddak.hellogin.github

import com.wonddak.hellogin.core.Error
import com.wonddak.hellogin.core.HelloginDefaultProvider
import com.wonddak.hellogin.core.LoginRequester
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

    /**
     * requestLogin
     */
    override suspend fun requestLogin() {
        require(optionProvider != null) { "optionProvider not init" }
        provider.startGithubLogin(optionProvider!!)
    }

    suspend fun requestAuth(code:String) {
        val tokenHandler = HelloginDefaultProvider.getAnyTokenHandler()
        client.requestAccessToken(AuthRequestData(optionProvider!!.provideClientData(),code))
            .onSuccess {
                tokenHandler.onSuccess(it)
            }.onFailure {
                tokenHandler.onFail(it as Error)
            }
    }
}

/**
 * expect Class For github Login
 */
internal expect class GithubLoginProvider() {
    /**
     * Start Request For Google Login
     */
    suspend fun startGithubLogin(
        optionProvider: GithubOptionProvider,
    )
}

/**
 * Option Provider Default Interface
 */
interface GithubOptionProvider {
    /**
     * request For Code
     */
    fun provideLoginId(): CodeRequestData

    /**
     * request For AccessToken
     */
    fun provideClientData() : ClientData

    /**
     * CallBack Scheme
     */
    fun provideScheme(): String
}