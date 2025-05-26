package com.transsion.financialassistant.search.screens.search

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall
import com.transsion.financialassistant.search.model.SearchView
import com.transsion.financialassistant.search.screens.components.CustomSearchBar
import com.transsion.financialassistant.search.screens.components.ListItemUI
import com.transsion.financialassistant.search.screens.components.RecentSearchUi
import com.transsion.financialassistant.search.screens.components.TransactionUiListItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavController = rememberNavController()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    var showRecentSearches by remember { mutableStateOf(false) }

    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = paddingSmall, end = paddingSmall)
        ) {

            //Top containing search bar and back navigation icon

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //back navigation
                IconButton(
                    onClick = { navController.navigateUp() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                //search Bar
                CustomSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    query = state.searchQuery,
                    onQueryChanged = {
                        viewModel.onQueryChanged(it)
                        if (it.isEmpty()) viewModel.onSearchViewChanged(SearchView.INITIAL)
                        else viewModel.onSearchViewChanged(SearchView.ON_SEARCH)
                    },
                    onFocusChanged = {
                        //focus used to control visibility of the recent searches
                        showRecentSearches = it
                    }
                )
            }

            VerticalSpacer(5)

            AnimatedContent(
                modifier = Modifier.fillMaxSize(),
                targetState = state.searchView
            ) {
                when (it) {
                    SearchView.INITIAL -> {

                        Column(modifier = Modifier.fillMaxSize()) {
                            //Recent Searches
                            androidx.compose.animation.AnimatedVisibility(visible = showRecentSearches) {
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
                                            items(viewModel.recentSearchQueries) { recentQuery ->
                                                RecentSearchUi(item = recentQuery)
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
                                TitleText(text = "Frequent Senders")
                                VerticalSpacer(10)
                                //list of senders
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    viewModel.frequentSenders.forEach { sender ->
                                        ListItemUI(transactionUi = sender)
                                    }
                                }
                            }

                            VerticalSpacer(20)

                            //Frequent Recipients
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(paddingMedium),
                            ) {
                                //title
                                TitleText(text = "Frequent Recipients")
                                VerticalSpacer(10)
                                //list of recipients
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    viewModel.frequentRecipients.forEach { recipient ->
                                        ListItemUI(transactionUi = recipient)
                                    }
                                }
                            }
                        }
                    }

                    SearchView.ON_SEARCH -> {
                        Column {
                            //Money in
                            TitleText(text = stringResource(com.transsion.financialassistant.presentation.R.string.money_in))
                            VerticalSpacer(5)
                            LazyColumn {
                                items(viewModel.moneyIn) { moneyIn ->
                                    TransactionUiListItem(transactionUi = moneyIn)
                                }
                            }

                            VerticalSpacer(10)

                            //Money Out
                            TitleText(text = stringResource(com.transsion.financialassistant.presentation.R.string.money_out))
                            VerticalSpacer(5)
                            LazyColumn {
                                items(viewModel.moneyOut) { moneyOut ->
                                    TransactionUiListItem(transactionUi = moneyOut)
                                }
                            }

                        }
                    }
                }
            }


        }
    }
}