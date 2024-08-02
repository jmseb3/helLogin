package com.wonddak.hellogin.core

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