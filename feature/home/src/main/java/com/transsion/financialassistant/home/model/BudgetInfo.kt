package com.transsion.financialassistant.home.model

// Data class representing the budget details
data class BudgetInfo(
    val category: String,
    val recurrence: String,
    val title: String,
    val currentAmount: Double,
    val limitAmount: Double,
    val message: String
)