package com.transsion.financialassistant.search.screens.search

import com.transsion.financialassistant.data.models.Frequent
import com.transsion.financialassistant.search.model.SearchView

data class SearchScreenState(
    val searchView: SearchView = SearchView.INITIAL,
    val searchQuery: String = "",
    val frequentSenders: List<Frequent> = emptyList(),
    val frequentRecipients: List<Frequent> = emptyList(),
)

