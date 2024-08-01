package com.wonddak.hellogin.core

import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene

fun topViewController(): Container {
    val presentingViewController = ((UIApplication.sharedApplication().connectedScenes()
        .first() as? UIWindowScene)?.windows() as List<UIWindow?>).first()
        ?.rootViewController()!!
    return presentingViewController
}

fun setDefaultTopController() {
    LoginDefaultOptionProvider.setContainer(topViewController())
}