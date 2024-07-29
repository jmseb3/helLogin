package com.wonddak.hellogin.core

sealed interface ButtonTheme {

    /**
     * Light mode
     */
    data object Light : ButtonTheme

    /**
     * Dark mode
     */
    data object Dark : ButtonTheme
}

sealed interface ButtonType {

    /**
     * Icon Only
     */
    data object IconOnly : ButtonType

    /**
     * Icon With Text
     */
    data class WithText(val text: String) : ButtonType
}