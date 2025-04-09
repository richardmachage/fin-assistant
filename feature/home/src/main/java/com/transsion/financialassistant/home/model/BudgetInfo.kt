package com.transsion.financialassistant.home.model

// Data class representing the budget details
data class BudgetInfo(
    val category: String,            // e.g., "Budgets"
    val recurrence: String,         // e.g., "Recurrent"
    val title: String,              // e.g., "Household Budget"
    val currentAmount: Double,      // e.g., 37785.00
    val limitAmount: Double,        // e.g., 40000.00
    val message: String             // e.g., "You’ve kept the discipline so far. Everything is good."
)