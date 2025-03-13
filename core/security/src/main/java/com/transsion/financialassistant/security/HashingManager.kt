package com.transsion.financialassistant.security

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object HashingManager {

    fun generateSalt() = ByteArray(32).apply { SecureRandom().nextBytes(this) }

    fun hashData(data: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(data.toCharArray(), salt, 10000, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(spec).encoded
    }
}