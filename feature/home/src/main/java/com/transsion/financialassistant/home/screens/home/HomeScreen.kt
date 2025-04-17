package com.transsion.financialassistant.home.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.TransactionUi
import com.transsion.financialassistant.home.navigation.HomeRoutes
import com.transsion.financialassistant.home.screens.components.InsightCateToggleSegmentedButton
import com.transsion.financialassistant.home.screens.components.MpesaBalanceCard
import com.transsion.financialassistant.home.screens.components.MyBudgetsCard
import com.transsion.financialassistant.home.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val recents by viewModel.recentTransactions.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )
    val mpesaBalance by viewModel.mpesaBalance.collectAsState()
    val numOfAllTransactions by viewModel.numOfAllTransactions.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(color = FAColors.greenOverlay)
                            .border(
                                width = 1.dp,
                                color = FAColors.green,
                                shape = CircleShape
                            )
                            .clickable {
                                //TODO
                            }
                    ) {
                        Icon(
                            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.start_avatar),
                            contentDescription = null,
                            tint = FAColors.green
                        )
                    }
                },
                title = {
                    Column {
                        BigTittleText(
                            text = viewModel.getGreetingBasedOnTime(context)
                        )

                    }
                },
                actions = {
                    //search
                    /*IconButtonFa(
                        icon = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.search),
                        colors = colors(),
                        onClick = {
                            //TODO navigate to search screen
                        }
                    )*/
                    //more

                    /*IconButtonFa(
                        icon = Icons.Default.MoreVert,
                        colors = colors(),
                        onClick = {
                            //TODO
                        }
                    )*/
                }
            )
        },
        floatingActionButton = {
            /*FloatingActionButton(
                modifier = Modifier.offset(y = (35).dp),
                onClick = {
                    //TODO
                },
                containerColor = FAColors.green,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }*/
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding()),

            ) {

            MpesaBalanceCard(
                balance = mpesaBalance.toString().formatAsCurrency(),
                moneyIn = state.moneyIn,
                moneyOut = state.moneyOut,
                insightCategory = state.insightCategory
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingSmall),
                contentAlignment = Alignment.Center
            ) {
                InsightCateToggleSegmentedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    selectedOption = state.insightCategory,
                    onOptionSelected = {
                        viewModel.onInsightCategoryChange(it)
                    }
                )
            }

            VerticalSpacer(10)
            HorizontalDivider()

            //Recent Transactions
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //tittle
                TitleText(text = stringResource(R.string.recent_transactions))

                //view all
                val viewAll = stringResource(R.string.view_all)
                ClickableText(
                    text = "$viewAll ($numOfAllTransactions)",
                    onClick = {
                        navController.navigate(HomeRoutes.AllTransactions(insightCategory = state.insightCategory))
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium)
                ///.heightIn(max = (screenHeight / 4).dp),
                ) {
                items(recents.size) {
                    val item = recents[it]
                        TransactionUiListItem(
                            transactionUi = TransactionUi(
                                title = item.name ?: item.transactionCode,//"NAIVAS",
                                type = item.transactionType,//if (it % 2 != 0) TransactionType.SEND_POCHI else TransactionType.BUY_GOODS,
                                amount = item.amount.toString(),//"50.00",
                                inOrOut = item.transactionCategory,//if (it % 2 != 0) TransactionCategory.OUT else TransactionCategory.IN,
                                dateAndTime = item.date//"Jan 12, 9:47 AM"
                            )
                        )
                    }
                }
            }


            if (false) {
                HorizontalDivider()

                LazyRow(
                modifier = Modifier
                    .padding(paddingMedium)
            ) {
                items(5)
                {
                    MyBudgetsCard()
                }
            }
            }
        }
    }


@Composable
private fun colors() = IconButtonColors(
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onBackground,
    disabledContainerColor = Color.Transparent,
    disabledContentColor = Color.Transparent
)

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}