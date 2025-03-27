package com.transsion.financialassistant.data.repository.transaction.bundles_purchase

import android.content.Context

interface BundlesPurchaseRepo {
    suspend fun insertBundlesPurchaseTransaction(
        message: String,
        context: Context,
        subId: Int,
        phone: String? = null,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}