package com.wonddak.hellogin.core

/**
 * ERROR CLASS per platform
 * - Android : Throwable
 * - Ios : NsError
 */
expect class Error

/**
 * get error description
 */
expect fun Error.getMessage() :String