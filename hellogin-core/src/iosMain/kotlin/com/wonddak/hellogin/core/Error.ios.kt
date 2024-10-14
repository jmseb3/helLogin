package com.wonddak.hellogin.core

import platform.Foundation.NSError

actual typealias Error = NSError

fun makeMsgError(msg:String) : NSError {
    println("[makeMsgError] : $msg")
    return NSError(
        domain = null,
        code = -1,
        userInfo =
            mapOf(
                "msg" to msg
            )
    )
}

actual fun Error.getMessage(): String {
    return this.localizedDescription()
}