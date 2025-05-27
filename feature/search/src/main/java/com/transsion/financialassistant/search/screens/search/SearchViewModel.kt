package com.transsion.financialassistant.search.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.search.domain.SearchRepo
import com.transsion.financialassistant.search.model.SearchView
import com.transsion.financialassistant.search.model.TransactionUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepo: SearchRepo
) : ViewModel() {
    private var _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()


    init {
        getFrequentSenders()
        getFrequentRecipients()
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults = _state.map { it.searchQuery }
        .distinctUntilChanged()
        .debounce(100)
        .flatMapLatest {
            if (it.isNotBlank())
                searchRepo.onSearch(it)
            else flow { }
        }
        .onStart {
            //TODO show some loading of needed
        }
        .cachedIn(viewModelScope)


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

    private fun getFrequentRecipients() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    frequentRecipients = searchRepo.getFrequentRecipients()
                )
            }
        }
    }


    private fun getFrequentSenders() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    frequentSenders = searchRepo.getFrequentSenders()
                )
            }
        }
    }


    val recentSearchQueries = searchRepo.getRecentSearches()
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun onQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun onSearchViewChanged(view: SearchView) {
        if (state.value.searchView != view) {
            _state.update { it.copy(searchView = view) }
        }
    }

    fun saveRecent() {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.saveRecentSearch(state.value.searchQuery.trim())
        }
    }

    fun onDeleteRecent(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.deleteRecentSearch(id)
        }
    }


}