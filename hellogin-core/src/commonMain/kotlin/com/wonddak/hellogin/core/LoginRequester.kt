package com.wonddak.hellogin.core

/**
 * Base of Login Interface
 */
interface LoginRequester<T> {

    /**
     * Request Login
     */
    suspend fun requestLogin(tokenHandler : TokenResultHandler<T>)
}

/**
 * Result Handler For T
 * T is Token Per Auth
 */
interface TokenResultHandler<T> {
    fun onSuccess(token: T)
    fun onFail(error: Error?)
}

/**
 * All Module Default Option
 */
object HelloginContainerProvider {
    private var container: Container? = null
    fun setContainer(container: Container) {
        this.container = container
    }

    fun getContainer(): Container {
        require(container != null) { "container not init" }
        return container!!
    }
}
