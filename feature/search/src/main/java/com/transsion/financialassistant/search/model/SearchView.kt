package com.transsion.financialassistant.search.model

enum class SearchView(val description: String) {
    INITIAL("this is the first view loaded, before any user interactions."),
    ON_SEARCH("When search query is not empty")
}