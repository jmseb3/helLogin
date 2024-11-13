package com.wonddak.hellogin.provider

import com.wonddak.hellogin.BuildKonfig
import com.wonddak.hellogin.apple.AppleOptionProviderAndroid
import com.wonddak.hellogin.apple.AppleSignInRequestScope

class MyAppleOptionProvider : AppleOptionProviderAndroid {
    override val mClientId: String
        get() = BuildKonfig.appleClientId

    override val mRedirectUrl: String
        get() = BuildKonfig.appleRedirectUrl

    override val requestScope: AppleSignInRequestScope
        get() = AppleSignInRequestScope.FullNameAndEmail
}