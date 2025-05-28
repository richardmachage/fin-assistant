package com.transsion.financialassistant.search.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.transsion.financialassistant.search.domain.SearchRepo
import com.transsion.financialassistant.search.model.SearchView
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


    val recentSearches = searchRepo.getRecentSearches()
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
            state.value.searchQuery.ifBlank { return@launch }
            searchRepo.saveRecentSearch(state.value.searchQuery.trim())
        }
    }

    fun onDeleteRecent(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.deleteRecentSearch(id)
        }
    }


}