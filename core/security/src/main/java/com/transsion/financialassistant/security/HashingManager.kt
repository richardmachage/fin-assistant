package com.transsion.financialassistant.security

import android.util.Base64
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

    fun saltToString(salt: ByteArray): String = Base64.encodeToString(salt, Base64.DEFAULT)

    fun stringToSalt(saltString: String): ByteArray = Base64.decode(saltString, Base64.DEFAULT)

}