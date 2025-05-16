package com.transsion.financialassistant.security

import android.util.Base64
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.nio.charset.Charset

@RunWith(AndroidJUnit4::class)
class EncryptionManagerTests {

    @Test
    fun testKeystoreGeneratesAndRetrievesSameKey() {
        val key1 = EncryptionManager.getEncryptionKey()
        val key2 = EncryptionManager.getEncryptionKey()

        assertNotNull(key1)
        assertNotNull(key2)

        assertEquals(key1.encoded.contentToString(), key2.encoded.contentToString())
    }

    @Test
    fun testIvIs12BytesLong() {
        val data = "test".toByteArray()
        val encryptedData = EncryptionManager.encryptData(data)

        val iVBytes = encryptedData.getIvAsByteArray()
        assertEquals(12, iVBytes.size)
    }

    @Test
    fun testEncryptionProducesValidOutput() {
        val data = "encryptMe".toByteArray()
        val encryptedData = EncryptionManager.encryptData(data)

        assertNotNull(encryptedData)
        assertTrue(encryptedData.data.isNotEmpty())
    }

    @Test
    fun testEncryptionAndDecryptionConsistency() {
        val dataToEncrypt = "Hello secure me!".toByteArray()
        val encryptedData = EncryptionManager.encryptData(dataToEncrypt)
        val decryptedData = EncryptionManager.decryptData(encryptedData)

        assertArrayEquals(dataToEncrypt, decryptedData)
    }

    @Test
    fun testSameDataEncryptsDifferentlyEachTimeDueToIv() {
        val dataToEncrypt = "Hii ni sensitive data mzee".toByteArray()
        val encryptedData1 = EncryptionManager.encryptData(dataToEncrypt)
        val encryptedData2 = EncryptionManager.encryptData(dataToEncrypt)

        assertNotEquals(encryptedData1.data, encryptedData2.data)
    }

    @Test
    fun testDecryptionFailsWhenEncryptedDataIsModified() {
        val data = "Confidential Data".toByteArray(Charset.defaultCharset())
        val encrypted = EncryptionManager.encryptData(data)

        // Modify the encrypted data (simulate attack)
        val modifiedData = encrypted.data.toByteArray().apply { this[0] = (this[0] + 1).toByte() }
        val modifiedEncrypted =
            EncryptedData(encrypted.iv, Base64.encodeToString(modifiedData, Base64.DEFAULT))

        assertThrows("Decryption should fail", Exception::class.java) {
            EncryptionManager.decryptData(modifiedEncrypted)
        }
    }

    @Test
    fun testSaltToStringConversion() {
        val salt = HashingManager.generateSalt()
        val saltString = HashingManager.saltToString(salt)
        val saltFromString = HashingManager.stringToSalt(saltString)

        assertTrue(salt.contentEquals(saltFromString))

    }
}