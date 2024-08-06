package com.wonddak.hellogin.github

import com.wonddak.hellogin.github.network.model.CodeRequestData

object GithubPath {
    const val BASE = "https://github.com"

    private const val LOGIN = "/login/oauth/authorize"
    const val ACCESS_TOKEN = "/login/oauth/access_token"


    fun getLoginPage(codeData: CodeRequestData): String {
        return BASE + LOGIN + codeData.getQueryString()
    }
}