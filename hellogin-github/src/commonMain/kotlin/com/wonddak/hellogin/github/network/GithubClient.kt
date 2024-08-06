package com.wonddak.hellogin.github.network

import com.wonddak.hellogin.core.BaseAuthClient
import com.wonddak.hellogin.github.GithubPath
import com.wonddak.hellogin.github.network.model.AuthRequestData
import com.wonddak.hellogin.github.network.model.GithubResult
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendEncodedPathSegments

internal class GithubClient : BaseAuthClient() {

    override val baseUrl: String
        get() = GithubPath.BASE


    suspend fun requestAccessToken(authRequestData: AuthRequestData): Result<GithubResult> {
        val response = client.get {
            url {
                appendEncodedPathSegments(GithubPath.ACCESS_TOKEN)
            }
            parameter("client_id", authRequestData.clientId)
            parameter("client_secret", authRequestData.clientSecret)
            parameter("code", authRequestData.code)
            if (authRequestData.redirectUri.isNotEmpty()) {
                parameter("redirect_uri", authRequestData.redirectUri)
            }
        }
        return runCatching { response.body() }
    }
}