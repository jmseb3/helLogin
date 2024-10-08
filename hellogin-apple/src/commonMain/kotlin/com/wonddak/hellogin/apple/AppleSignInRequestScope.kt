package com.wonddak.hellogin.apple

/***
 * Apple Sign in Request Scope that can be requested from user when first time user signup.
 * You can request from user [AppleSignInRequestScope.FullName] and [AppleSignInRequestScope.Email]
 */
sealed class AppleSignInRequestScope {
    /**
     * Request scope for user's fullname
     */
    data object FullName : AppleSignInRequestScope()

    /**
     * Request scope for user's email
     */
    data object Email : AppleSignInRequestScope()

    /**
     * Request scope for user's email
     */
    data object FullNameAndEmail : AppleSignInRequestScope()
}