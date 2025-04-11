package com.transsion.financialassistant.home.screens.all_transactions.filter

import com.transsion.financialassistant.data.models.TransactionCategory


data class FilterState(
    val source: TransactionCategory? = null,
    val period: FilterPeriod? = null,
    val selectedCategories: List<FilterCategory> = emptyList()
)

//enum class FilterSource { MONEY_IN, MONEY_OUT }

// Filter by Period Enum Class
enum class FilterPeriod(val label: String){
    MOST_RECENT("Most Recent"),
    OLDEST_FIRST("Oldest First"),
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    THIS_WEEK("This week"),
    LAST_WEEK("Last week"),
    THIS_MONTH("This month"),
    LAST_MONTH("Last month")
}

// Filter by Category Enum Class
enum class FilterCategory(val label: String) {
    BABY_CLOTHES("Baby Clothes"),
    GIFTS("Gifts & Donations"),
    BILLS("Bills & Utilities"),
    CLOTHING("Clothing"),
    GROCERIES("Groceries"),
    RENT("Rent"),
    SAVINGS("Savings"),
    TRANSACTION_COSTS("Transaction costs"),
    ENTERTAINMENT("Entertainment"),
    HEALTHCARE("Healthcare")
}
