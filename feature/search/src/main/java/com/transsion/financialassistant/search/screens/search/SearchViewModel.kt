package com.transsion.financialassistant.search.screens.search

import androidx.lifecycle.ViewModel
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.search.model.ListItem
import com.transsion.financialassistant.search.model.RecentSearchQuery
import com.transsion.financialassistant.search.model.SearchView
import com.transsion.financialassistant.search.model.TransactionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : ViewModel() {


    val moneyIn = listOf(
        TransactionUi(
            code = "GFARYHNGF",
            title = "Baba Vos",
            type = TransactionType.RECEIVE_MONEY,
            inOrOut = TransactionCategory.IN,
            amount = "56,780",
            dateAndTime = "Today, 9:47AM"
        ),
        TransactionUi(
            code = "FRYHNGF",
            title = "Fred Ouko",
            type = TransactionType.RECEIVE_MONEY,
            inOrOut = TransactionCategory.IN,
            amount = "56,780",
            dateAndTime = "Today, 9:47AM"
        ),

        TransactionUi(
            code = "EFGHTSFG",
            title = "Naivas SuperMarket special",
            type = TransactionType.RECEIVE_MONEY,
            inOrOut = TransactionCategory.IN,
            amount = "56,780",
            dateAndTime = "Today, 9:47AM"
        ),

        )

    val moneyOut = listOf(
        TransactionUi(
            code = "GFARYHNGF",
            title = "ISUZU CAR",
            type = TransactionType.PAY_BILL,
            inOrOut = TransactionCategory.OUT,
            amount = "56,780",
            dateAndTime = "Today, 9:47AM"
        ),
        TransactionUi(
            code = "GFARYHNGF",
            title = "Naivas SuperMarket special",
            type = TransactionType.BUY_GOODS,
            inOrOut = TransactionCategory.OUT,
            amount = "56,780",
            dateAndTime = "Today, 9:47AM"
        ),
        TransactionUi(
            code = "GFARYHNGF",
            title = "Kwa Mathe",
            type = TransactionType.SEND_POCHI,
            inOrOut = TransactionCategory.OUT,
            amount = "56,780",
            dateAndTime = "Today, 9:47AM"
        ),
    )


    val frequentRecipients = listOf(

        ListItem(
            transactionCode = "GFARYHNGF",
            title = "Naivas SuperMarket special",
            transactionType = TransactionType.BUY_GOODS,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Fred Ouko",
            transactionType = TransactionType.SEND_MONEY,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Equity Bank",
            transactionType = TransactionType.PAY_BILL,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Kevin Kiarie",
            transactionType = TransactionType.SEND_MONEY,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "GFARYHNGF",
            title = "Naivas SuperMarket special",
            transactionType = TransactionType.BUY_GOODS,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Fred Ouko",
            transactionType = TransactionType.SEND_MONEY,
            transactionCategory = TransactionCategory.OUT
        ),

        )

    val frequentSenders = listOf(
        ListItem(
            transactionCode = "GFARYHNGF",
            title = "Naivas SuperMarket special",
            transactionType = TransactionType.BUY_GOODS,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Fred Ouko",
            transactionType = TransactionType.SEND_MONEY,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Equity Bank",
            transactionType = TransactionType.PAY_BILL,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Kevin Kiarie",
            transactionType = TransactionType.SEND_MONEY,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "GFARYHNGF",
            title = "Naivas SuperMarket special",
            transactionType = TransactionType.BUY_GOODS,
            transactionCategory = TransactionCategory.OUT
        ),
        ListItem(
            transactionCode = "EFARYHNGF",
            title = "Fred Ouko",
            transactionType = TransactionType.SEND_MONEY,
            transactionCategory = TransactionCategory.OUT
        ),
    )

    val recentSearchQueries = listOf(
        RecentSearchQuery(id = 2L, query = "650"),
        RecentSearchQuery(id = 1L, query = "Pamela Makhoka"),
        RecentSearchQuery(id = 3L, query = "Equity Bank"),
        RecentSearchQuery(id = 4L, query = "Jeff Mwango"),
        RecentSearchQuery(id = 5L, query = "56,780"),
    )

    fun onQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun onSearchViewChanged(view: SearchView) {
        if (state.value.searchView != view) {
            _state.update { it.copy(searchView = view) }
        }
    }

    private var _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()


}