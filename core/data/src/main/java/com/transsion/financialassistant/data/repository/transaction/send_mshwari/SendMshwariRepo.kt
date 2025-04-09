package com.transsion.financialassistant.data.repository.transaction.send_mshwari

import android.content.Context

interface SendMshwariRepo {
    suspend fun insertSendMshwariTransaction(
        message: String,
        context: Context,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
        phone: String? = null
    )
}