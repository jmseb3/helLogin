package com.wonddak.hellogin.core

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

abstract class BaseAuthClient {

    abstract val baseUrl: String

    protected val client = HttpClient() {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
        defaultRequest {
            url(baseUrl)
        }
    }
}