package com.transsion.financialassistant.search.screens.search

import com.transsion.financialassistant.search.model.SearchView

data class SearchScreenState(
    val searchView: SearchView = SearchView.INITIAL,
    val searchQuery: String = "",
)

