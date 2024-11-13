package com.wonddak.hellogin.provider

import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.wonddak.hellogin.google.GoogleOptionProviderAndroid
import com.wonddak.hellogin.BuildKonfig

class MyGoogleOptionProvider : GoogleOptionProviderAndroid {

    override fun provideGoogleIdOption(): GetGoogleIdOption {
        return GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildKonfig.googleServerId)
            .build()
    }
}