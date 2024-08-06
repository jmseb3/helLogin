package com.wonddak.hellogin.github.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps#2-users-are-redirected-back-to-your-site-by-github
@Serializable
data class AuthRequestData(
    @SerialName("client_id")
    val clientId: String,
    @SerialName("client_secret")
    val clientSecret: String,
    @SerialName("code")
    val code: String,
    @SerialName("redirect_uri")
    val redirectUri: String = "",
) {
    constructor(
        clientData: ClientData,
        code: String,
    ) : this(clientData.clientId, clientData.clientSecret, code, clientData.redirectUri)
}

data class ClientData(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String = "",
)
