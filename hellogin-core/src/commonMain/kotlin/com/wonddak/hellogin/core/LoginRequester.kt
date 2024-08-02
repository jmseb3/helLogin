package com.wonddak.hellogin.core

/**
 * Base of Login Interface
 */
interface LoginRequester<T : Any> {
    suspend fun requestLogin()

}

/**
 * Result Handler For T
 * T is Token Per Auth
 */
interface TokenResultHandler {
    fun onSuccess(token: Any)
    fun onFail(error: Error?)
}


/**
 * All Module Default Option
 */
object HelloginDefaultProvider {
    private var container: Container? = null
    fun setContainer(container: Container) {
        this.container = container
    }

    fun getContainer(): Container {
        require(container != null) { "container not init" }
        return container!!
    }
    private var anyTokenResultHandler : TokenResultHandler? = null

    fun setAnyTokenHandler(anyTokenResultHandler: TokenResultHandler) {
        this.anyTokenResultHandler = anyTokenResultHandler
    }

    fun getAnyTokenHandler(): TokenResultHandler {
        require(anyTokenResultHandler != null) { "anyTokenResultHandler not init" }
        return anyTokenResultHandler!!
    }
}
