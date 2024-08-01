package com.wonddak.hellogin.github.network.model

//https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/authorizing-oauth-apps#web-application-flow
data class CodeRequestData(
    val clientId: String,
    val redirectUri: String = "",
    val login: String = "",
    val scope: String = "",
    val state: String = "",
    val allowSignup: String = "",
    val prompt: String = "",
) {
    fun getQueryString() :String {
        val st = StringBuilder()
        st.append("?")
        if (clientId.isEmpty()) {
            throw  IllegalArgumentException("client Id is not empty")
        }
        st.append("client_id=")
        st.append(clientId)
        if (redirectUri.isNotEmpty()) {
            st.append("&redirect_uri=")
            st.append(redirectUri)
        }
        if (login.isNotEmpty()) {
            st.append("&login=")
            st.append(login)
        }
        if (scope.isNotEmpty()) {
            st.append("&scope=")
            st.append(scope)
        }
        if (state.isNotEmpty()) {
            st.append("&state=")
            st.append(state)
        }
        if (allowSignup.isNotEmpty()) {
            st.append("&allow_signup=")
            st.append(allowSignup)
        }
        if (prompt.isNotEmpty()) {
            st.append("&prompt=")
            st.append(prompt)
        }

        return st.toString()
    }
}