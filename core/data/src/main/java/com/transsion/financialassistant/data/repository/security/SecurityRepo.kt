package com.transsion.financialassistant.data.repository.security

import com.transsion.financialassistant.security.EncryptedData


interface SecurityRepo {
    //fun getHashingSalt(): String

    fun doHash(value: String): ByteArray

    fun encryptData(data: ByteArray): EncryptedData

    fun decryptData(data: String): ByteArray
}