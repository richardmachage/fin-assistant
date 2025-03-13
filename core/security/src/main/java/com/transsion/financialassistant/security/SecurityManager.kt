package com.transsion.financialassistant.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

object SecurityManager {
    private const val KEYSTORE_ALIAS = "PIN_ENCRYPTION_KEY"
    private const val PREF_NAME = "secure_prefs"
    private const val PIN_HASH_KEY = "encrypted_hashed_pin"
    private const val IV_KEY = "pin_iv"
    private const val SALT_KEY = "pin_salt"

    private fun getEncryptionKey(): SecretKey {
        val keystore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

        if (keystore.containsAlias(KEYSTORE_ALIAS)) {
            val keyGen =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            keyGen.init(
                KeyGenParameterSpec.Builder(
                    KEYSTORE_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .build()
            )
            keyGen.generateKey()
        }
        return keystore.getKey(KEYSTORE_ALIAS, null) as SecretKey
    }
}