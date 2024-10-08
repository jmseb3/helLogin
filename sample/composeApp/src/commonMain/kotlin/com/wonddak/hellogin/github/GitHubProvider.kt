package com.wonddak.hellogin.github

import com.wonddak.hellogin.github.network.model.ClientData
import com.wonddak.hellogin.github.network.model.CodeRequestData
import com.wonddak.helloginp.BuildKonfig

internal class GitHubProvider : GithubOptionProvider {
    companion object {
        const val GITHUB_CLIENT_ID = "Ov23lit8TJVzwkKKxmyq"
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