package com.transsion.financialassistant.home.screens.home

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.getMessageForTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.TransactionUi
import com.transsion.financialassistant.home.navigation.HomeRoutes
import com.transsion.financialassistant.home.screens.components.InsightCateToggleSegmentedButton
import com.transsion.financialassistant.home.screens.components.MpesaBalanceCard
import com.transsion.financialassistant.home.screens.components.MyBudgetsCard
import com.transsion.financialassistant.home.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.components.bottom_sheets.BottomSheetFa
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    goToFeedBack: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val recents by viewModel.recentTransactions.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )
    val mpesaBalance by viewModel.mpesaBalance.collectAsState()
    val numOfAllTransactions by viewModel.numOfAllTransactions.collectAsState()
    val hideBalance = viewModel.hideBalance.collectAsState(false)
    var showMessageBottomSheet by remember { mutableStateOf(false) }
    var selectedMessage by remember { mutableStateOf("") }
    // var selectedMessageTransactionType by remember { mutableStateOf("") }
    var selectedTransaction by remember { mutableStateOf<UnifiedTransactionPersonal?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(paddingMedium),
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
                    TitleText(
                        modifier = Modifier
                            .padding(start = paddingSmall)
                            .fillMaxWidth(),
                        text = "Financial Assistant",//viewModel.getGreetingBasedOnTime(context),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                        )

                },
                actions = {
                    //feedback
                    IconButtonFa(
                        icon = painterResource(com.transsion.financialassistant.presentation.R.drawable.fluent_person_feedback_24_regular),
                        //colors = colors(),
                        onClick = {
                            goToFeedBack()
                        }
                    )

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
                .padding(top = innerPadding.calculateTopPadding())
                .padding(start = paddingMedium, end = paddingMedium),

            ) {

            MpesaBalanceCard(
                balance = mpesaBalance.toString().formatAsCurrency(),
                moneyIn = viewModel.moneyInToday.collectAsState().value.toString()
                    .formatAsCurrency(),
                moneyOut = viewModel.moneyOutToday.collectAsState().value.toString()
                    .formatAsCurrency(),
                insightCategory = state.insightCategory,
                onHideBalance = {
                    viewModel.onHideBalance(
                        hideBalance.value.not()
                    )
                },
                hide = hideBalance.value
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
                    .padding()
                ///.heightIn(max = (screenHeight / 4).dp),
            ) {
                items(recents.size) { it ->
                    val item = recents[it]
                    TransactionUiListItem(

                        transactionUi = TransactionUi(
                            title = item.name ?: item.transactionCode,
                            type = item.transactionType,
                            amount = item.amount.toString(),
                            inOrOut = item.transactionCategory,
                            dateAndTime = "${item.date.toMonthDayDate()}, ${item.time.toAppTime()}"
                        ),
                        onClick = {
                            getMessageForTransaction(
                                context = context,
                                transactionCode = item.transactionCode
                            )
                                .apply {
                                    onSuccess { message ->
                                        selectedTransaction = item
                                        selectedMessage = message
                                        showMessageBottomSheet = true

                                    }

                                    onFailure { error ->
                                        Toast.makeText(
                                            context,
                                            error.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        }
                    )
                }
            }
        }


        BottomSheetFa(
            modifier = Modifier,
            isSheetOpen = showMessageBottomSheet,
            onDismiss = {
                showMessageBottomSheet = false
                selectedMessage = ""
            }
        ) {
            if (selectedMessage.isNotBlank()) {
                Column(
                    modifier = Modifier
                        .padding(start = paddingLarge, end = paddingLarge)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5)),
                    // .background(FAColors.green),

                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    selectedTransaction?.let { transaction ->

                        //tittle -> Transaction type
                        TitleText(
                            text = transaction.transactionType.description,
                            textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)

                        )
                        VerticalSpacer(20)


                        //Message
                        Text(
                            modifier = Modifier.padding(bottom = paddingLarge),
                            text = selectedMessage,
                            textAlign = TextAlign.Left,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                            lineHeight = 18.sp
                        )
                        //Message
                        /*NormalText(
                            text = selectedMessage,
                            textAlign = TextAlign.Left
                        )*/
                        VerticalSpacer(10)
                        if (transaction.transactionType == TransactionType.SEND_MONEY) {
                            OutlineButtonFa(
                                text = "Reverse Transaction",
                                onClick = {
                                    //TODO
                                    Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    }
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
    HomeScreen(rememberNavController(), goToFeedBack = {})
}


