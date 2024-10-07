package com.wonddak.hellogin.apple

import com.wonddak.hellogin.core.TokenResultHandler

actual typealias AppleCred = Any

internal actual class AppleLoginProvider actual constructor() {
    actual suspend fun startAppleLogin(
        requestScopes: List<AppleSignInRequestScope>,
        tokenHandler: TokenResultHandler<AppleResult>
    ) {
    }

}

