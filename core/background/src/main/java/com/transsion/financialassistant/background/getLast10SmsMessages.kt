package com.transsion.financialassistant.background

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony
import com.transsion.financialassistant.background.models.SmsMessage

private fun getLast10SmsMessages(contentResolver: ContentResolver): List<SmsMessage> {
    val messages = mutableListOf<SmsMessage>()
    val uri = Uri.parse("content://sms")
    val projection = arrayOf(
        Telephony.Sms._ID,
        Telephony.Sms.ADDRESS,
        Telephony.Sms.BODY,
        Telephony.Sms.DATE,
        Telephony.Sms.TYPE
    )


    val sortOrder = "${Telephony.Sms.DATE} DESC LIMIT 10" // Get last 10 messages

    try {
        val cursor: Cursor? = contentResolver.query(uri, projection, null, null, sortOrder)
        cursor?.use {
            val idColumn = it.getColumnIndexOrThrow(Telephony.Sms._ID)
            val addressColumn = it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
            val bodyColumn = it.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val dateColumn = it.getColumnIndexOrThrow(Telephony.Sms.DATE)
            val typeColumn = it.getColumnIndexOrThrow(Telephony.Sms.TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idColumn)
                val address = it.getString(addressColumn)
                val body = it.getString(bodyColumn)
                val date = it.getLong(dateColumn)
                val type = it.getInt(typeColumn)
                messages.add(SmsMessage(id, address, body, date, type))
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return messages
}
