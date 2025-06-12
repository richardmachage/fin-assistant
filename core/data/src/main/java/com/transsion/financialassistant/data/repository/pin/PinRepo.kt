package com.transsion.financialassistant.data.repository.pin

interface PinRepo {

    /** sets/updates the PIN*/
    suspend fun setPin(
        pin: String,
        onSuccess: () -> Unit,
        onFailure: (errorMessage: String) -> Unit
    )

    fun isPinSet(): Boolean

    fun deletePin()
}