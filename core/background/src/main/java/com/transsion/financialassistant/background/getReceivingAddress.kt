package com.transsion.financialassistant.background

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionManager
import androidx.annotation.RequiresPermission

@SuppressLint("NewApi")
@RequiresPermission(anyOf = [Manifest.permission.READ_PHONE_NUMBERS, "carrier privileges", "android.permission.READ_PRIVILEGED_PHONE_STATE"])
fun getReceivingAddress(
    context: Context,
    subscriptionId: Int
): String? {

    return when (subscriptionId) {
        -1 -> null
        else -> {
            val subscriptionManager =
                context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
            subscriptionManager.getPhoneNumber(subscriptionId)
        }
    }
}