package com.wonddak.hellogin.core

actual typealias Error = Throwable

actual fun Error.getMessage(): String {
    return this.message ?: "unknown Error"
}