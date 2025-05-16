package com.transsion.financialassistant.admin.screens.more_details

data class MoreDetailsScreenState(
    val isLoading: Boolean = true,
    val title: String,
    val description: String,
    val photoUrl: String?,
    val isSolved: Boolean,
    val toastMessage: String? = null
)