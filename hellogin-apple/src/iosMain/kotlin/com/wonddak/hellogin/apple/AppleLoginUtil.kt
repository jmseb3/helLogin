package com.wonddak.hellogin.apple

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.UByteVar
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.refTo
import kotlinx.cinterop.usePinned
import platform.CoreCrypto.CC_SHA256
import platform.CoreCrypto.CC_SHA256_DIGEST_LENGTH
import platform.Security.SecRandomCopyBytes
import platform.Security.errSecSuccess
import platform.Security.kSecRandomDefault

object AppleLoginUtil {
    fun randomNonceString(length: Int = 32): String {
        require(length > 0) { "Length must be greater than 0" }
        val randomBytes = iosSecureRandomBytes(length)
        val charset = "0123456789ABCDEFGHIJKLMNOPQRSTUVXYZabcdefghijklmnopqrstuvwxyz-._"
        val nonce = randomBytes.map { byte ->
            charset[(byte.toInt() and 0xFF) % charset.length]
        }.joinToString("")

        return nonce

    }

    @OptIn(ExperimentalForeignApi::class)
    private fun iosSecureRandomBytes(length: Int): ByteArray {
        require(length > 0) { "Length must be greater than 0" }
        return memScoped {
            val randomBytes = allocArray<UByteVar>(length)
            val errorCode = SecRandomCopyBytes(kSecRandomDefault, length.convert(), randomBytes)
            if (errorCode != errSecSuccess) {
                throw RuntimeException("Unable to generate random bytes. SecRandomCopyBytes failed with OSStatus $errorCode")
            }
            randomBytes.readBytes(length)
        }
    }

    @OptIn(ExperimentalForeignApi::class, ExperimentalStdlibApi::class)
    fun sha256(input: String): String {
        val hashedData = UByteArray(CC_SHA256_DIGEST_LENGTH)
        val inputData = input.encodeToByteArray()
        inputData.usePinned {
            CC_SHA256(it.addressOf(0), inputData.size.convert(), hashedData.refTo(0))
        }
        return hashedData.toByteArray().toHexString(HexFormat.Default)
    }
}