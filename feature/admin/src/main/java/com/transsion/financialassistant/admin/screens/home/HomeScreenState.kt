package com.transsion.financialassistant.admin.screens.home

import com.transsion.financialassistant.admin.model.FeedBack

data class HomeScreenState(
    val isLoading: Boolean = false,
    val toastMessage: String? = null,
    val feedbacks: List<FeedBack> = emptyList()
)
