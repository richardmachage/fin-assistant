package com.transsion.financialassistant.security

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

object EncryptionManager {
    private const val KEYSTORE_ALIAS = "PIN_ENCRYPTION_KEY"


    private fun getEncryptionKey(): SecretKey {
        val keystore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

        if (keystore.containsAlias(KEYSTORE_ALIAS).not()) {
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


    fun encryptData(data: ByteArray): EncryptedData {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getEncryptionKey())
        return EncryptedData(
            iv = Base64.encodeToString(cipher.iv, Base64.DEFAULT),
            data = Base64.encodeToString(cipher.doFinal(data), Base64.DEFAULT)
        )
    }

    fun decryptData(encryptedData: EncryptedData): ByteArray {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(
            Cipher.DECRYPT_MODE,
            getEncryptionKey(),
            GCMParameterSpec(128, encryptedData.getIvAsByteArray())
        )
        return cipher.doFinal(encryptedData.getDataAsByteArray())
    }

}