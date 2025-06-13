package com.transsion.financialassistant.search.screens.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall
import com.transsion.financialassistant.search.R
import com.transsion.financialassistant.search.model.SearchView
import com.transsion.financialassistant.search.model.TransactionUi
import com.transsion.financialassistant.search.screens.components.CustomSearchBar
import com.transsion.financialassistant.search.screens.components.ListItemUI
import com.transsion.financialassistant.search.screens.components.RecentSearchUi
import com.transsion.financialassistant.search.screens.components.TransactionUiListItem

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showRecentSearches by remember { mutableStateOf(false) }
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val recentSearches by viewModel.recentSearches.collectAsState(initial = emptyList())
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BigTittleText(text = "Search")
                }
            )
        }
    ) { innerPadding ->
        val focusManager = LocalFocusManager.current

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .heightIn(max = screenHeight.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        focusManager.clearFocus()
                        showRecentSearches = false
                    }
                )
                .padding(innerPadding)
                .padding(start = paddingSmall, end = paddingSmall)

        ) {


            //search Bar
            CustomSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingSmall),
                query = state.searchQuery,
                onQueryChanged = {
                    viewModel.onQueryChanged(it)
                    if (it.isEmpty()) viewModel.onSearchViewChanged(SearchView.INITIAL)
                    else viewModel.onSearchViewChanged(SearchView.ON_SEARCH)
                },
                onFocusChanged = {
                    //focus used to control visibility of the recent searches
                    showRecentSearches = it

                    //when focus is lost, meaning the user is no longer searching
                    if (!it) {
                        viewModel.saveRecent()
                    }
                },
                clearFocus = {
                    focusManager.clearFocus()
                }
            )

            VerticalSpacer(5)

            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = state.searchView
            ) {
                when (it) {
                    SearchView.INITIAL -> {

                        Column(modifier = Modifier.fillMaxSize()) {
                            //Recent Searches
                            androidx.compose.animation.AnimatedVisibility(visible = showRecentSearches && recentSearches.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(paddingMedium)
                                ) {

                                    TitleText(text = "Recent Searches")
                                    VerticalSpacer(10)

                                    //list of recent searches
                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(paddingSmall)
                                    ) {
                                        LazyColumn {
                                            items(
                                                recentSearches
                                            ) { recentQuery ->
                                                RecentSearchUi(
                                                    item = recentQuery,
                                                    onClick = {
                                                        viewModel.onQueryChanged(query = recentQuery.query)
                                                        viewModel.onSearchViewChanged(SearchView.ON_SEARCH)
                                                    },
                                                    onDelete = {
                                                        viewModel.onDeleteRecent(id = recentQuery.id)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            //Frequent Senders
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(paddingMedium),
                            ) {
                                //title
                                TitleText(text = "Frequent Money Out")
                                VerticalSpacer(25)
                                //list of senders
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalArrangement = Arrangement.spacedBy(25.dp)
                                ) {
                                    state.frequentSenders.forEach { sender ->
                                        ListItemUI(
                                            name = sender.name ?: "Null",
                                            onClick = {
                                                sender.name?.let { n ->
                                                    viewModel.onQueryChanged(n)
                                                    viewModel.onSearchViewChanged(SearchView.ON_SEARCH)
                                                }
                                            }
                                        )
                                    }

                                }
                            }

                            VerticalSpacer(25)

                            //Frequent Recipients
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(paddingMedium),
                            ) {
                                //title
                                TitleText(text = "Frequent Money In")
                                VerticalSpacer(25)
                                //list of recipients
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    verticalArrangement = Arrangement.spacedBy(25.dp)
                                ) {
                                    state.frequentRecipients.forEach { recipient ->
                                        ListItemUI(
                                            name = recipient.name ?: "Null",
                                            onClick = {
                                                recipient.name?.let { n ->
                                                    viewModel.onQueryChanged(n)
                                                    viewModel.onSearchViewChanged(SearchView.ON_SEARCH)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    SearchView.ON_SEARCH -> {

                        if (searchResults.itemCount == 0) {
                            NoTransactionsFound()
                        } else {
                            //Money in
                            VerticalSpacer(5)
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(paddingMedium),
                            ) {

                                items(searchResults.itemCount) { index ->
                                    val item = searchResults[index]
                                    if (item != null /*&& item.transactionCategory == TransactionCategory.IN*/) {
                                        TransactionUiListItem(
                                            transactionUi = TransactionUi(
                                                title = item.name ?: item.transactionCode,
                                                type = item.transactionType,
                                                amount = item.amount.toString(),
                                                inOrOut = item.transactionCategory,
                                                dateAndTime = "${item.date.toMonthDayDate()}, ${item.time.toAppTime()}",
                                                code = item.transactionCode,
                                            ),
                                            onClick = {
                                                focusManager.clearFocus()
                                                //showRecentSearches = false
                                            }
                                        )
                                    }
                                }

                            }

                        }
                    }
                }
            }


        }
    }
}


@Composable
fun NoTransactionsFound() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        NormalText(text = stringResource(R.string.no_data_available))
    }
}