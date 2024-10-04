package com.wonddak.hellogin.core

import androidx.compose.ui.unit.ExperimentalUnitApi
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene

@ExperimentalUnitApi
fun topViewController(): Container {
    val presentingViewController = ((UIApplication.sharedApplication().connectedScenes()
        .first() as? UIWindowScene)?.windows() as List<UIWindow?>).first()
        ?.rootViewController()!!
    return presentingViewController
}

@ExperimentalUnitApi
fun setDefaultTopController() {
    HelloginContainerProvider.setContainer(topViewController())
}