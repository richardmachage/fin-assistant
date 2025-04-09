package com.transsion.financialassistant.data.repository.transaction.buy_goods

import android.content.Context

interface BuyGoodsRepo {

    suspend fun insertBuyGoodsTransaction(
        message: String,
        context: Context,
        phone: String? = null,
        subId: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    )
}