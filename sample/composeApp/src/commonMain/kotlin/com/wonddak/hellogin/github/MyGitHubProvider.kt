package com.wonddak.hellogin.github

import com.wonddak.hellogin.github.network.model.ClientData
import com.wonddak.hellogin.github.network.model.CodeRequestData
import com.wonddak.hellogin.BuildKonfig

internal class MyGitHubProvider : GithubOptionProvider {
    companion object {
        val GITHUB_CLIENT_ID = BuildKonfig.githubId
        val GITHUB_CLIENT_SECRET = BuildKonfig.githubSecret
    }

    override fun provideLoginId(): CodeRequestData {
        return CodeRequestData(
            GITHUB_CLIENT_ID
        )
    }

    override fun provideClientData(): ClientData {
        return ClientData(
            GITHUB_CLIENT_ID,
            GITHUB_CLIENT_SECRET
        )
    }

    override fun provideScheme(): String {
        return "hellogin"
    }
}