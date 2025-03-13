package com.transsion.financialassistant.security

import android.util.Base64

data class EncryptedData(
    val iv: String,
    val data: String
) {
    fun getIvAsByteArray(): ByteArray = Base64.decode(iv, Base64.DEFAULT)
    fun getDataAsByteArray(): ByteArray = Base64.decode(data, Base64.DEFAULT)
}
