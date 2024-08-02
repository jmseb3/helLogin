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

