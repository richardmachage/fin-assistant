package com.transsion.financialassistant.home.screens.home

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.repository.getMessageForTransaction
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toMonthDayDate
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.TransactionUi
import com.transsion.financialassistant.home.navigation.HomeRoutes
import com.transsion.financialassistant.home.screens.components.BottomSheetFaMessage
import com.transsion.financialassistant.home.screens.components.InsightCateToggleSegmentedButton
import com.transsion.financialassistant.home.screens.components.MpesaBalanceCard
import com.transsion.financialassistant.home.screens.components.MyBudgetsCard
import com.transsion.financialassistant.home.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.components.CategoryCard
import com.transsion.financialassistant.presentation.components.bottom_sheets.BottomSheetFa
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall
import com.transsion.financialassistant.settings.navigation.SettingRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    goToFeedBack: () -> Unit
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()

    val recents by viewModel.recentTransactions.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    val mpesaBalance by viewModel.mpesaBalance.collectAsState()
    val numOfAllTransactions by viewModel.numOfAllTransactions.collectAsState()
    val hideBalance = viewModel.hideBalance.collectAsState(false)
    var showMessageBottomSheet by remember { mutableStateOf(false) }
    var selectedMessage by remember { mutableStateOf("") }
    var selectedTransaction by remember { mutableStateOf<UnifiedTransactionPersonal?>(null) }
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(paddingMedium),
                title = {
                    BigTittleText(text = "Fin Assistant")
                },
                actions = {

                    //Settings
                    IconButtonFa(
                        icon = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.system_update_01),
                        colors = colors(),
                        onClick = {
                            navController.navigate(SettingRoutes.Settings)
                        }
                    )
                }
            )
        },

        ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .padding(start = paddingMedium, end = paddingMedium)
                .verticalScroll(rememberScrollState()),
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


            if (state.insightCategory == InsightCategory.PERSONAL) {
                VerticalSpacer(10)
                HorizontalDivider()
            }

            //Account Balances
            AnimatedVisibility(visible = state.insightCategory == InsightCategory.BUSINESS) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingSmall)
                ) {
                    // VerticalSpacer(10)

                    TitleText(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(paddingMedium),
                        text = stringResource(R.string.account_balances),
                        textAlign = TextAlign.Left
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingSmall),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        //Pochi
                        CategoryCard(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.pochi_la_biashara),
                            amount = viewModel.pochiBalance.collectAsState().value.toString()
                                .formatAsCurrency(),
                            icon = com.transsion.financialassistant.presentation.R.drawable.coins_01,

                            )

                        HorizontalSpacer(10)

                        //Till
                        CategoryCard(
                            modifier = Modifier.weight(1f),
                            title = stringResource(R.string.till_number),
                            amount = viewModel.tillBalance.collectAsState().value.toString()
                                .formatAsCurrency(),
                            icon = com.transsion.financialassistant.presentation.R.drawable.cash,

                            )
                    }

                    VerticalSpacer(10)
                    HorizontalDivider()

                }


            }
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
                    .heightIn(max = (screenHeight / 2).dp),
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

        val transaction = selectedTransaction
        if (showMessageBottomSheet && transaction != null && selectedMessage.isNotBlank()) {
            BottomSheetFaMessage(
                modifier = Modifier,
                transaction = transaction,
                showMessageBottomSheet = true,
                selectedMessage = selectedMessage,
                onDismiss = {
                    showMessageBottomSheet = false
                    selectedMessage = ""
                    selectedTransaction = null
                }
            )
        }


        /*BottomSheetFa(
            modifier = Modifier,
            isSheetOpen = showMessageBottomSheet,
            onDismiss = {
                showMessageBottomSheet = false
                selectedMessage = ""
            }
        ) {
            if (selectedMessage.isNotBlank()) {
                selectedTransaction?.let { transaction ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingLarge),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            // Header Row - Transaction type
                            *//*Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                TitleText(
                                    text = transaction.transactionType.description,
                                    fontSize = 16.sp,
                                )
                                Spacer(modifier = Modifier.weight(1f))
                            }

                            VerticalSpacer(20)*//*

                            TitleText(
                                text = transaction.transactionType.description,
                                fontSize = 16.sp,
                            )
                            VerticalSpacer(20)

                            // Centered Amount
                            BigTittleText(
                                text = "KES ${transaction.amount}",
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                            )

                            VerticalSpacer(20)

                            // Receiver Name
                            transaction.name?.let { receiverName ->
                                NormalText(
                                    text = "To: $receiverName",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                )
                                VerticalSpacer(10)
                            }

                            // Message without M-PESA balance
                            val sanitizedMessage = selectedMessage
                                .replace(
                                    Regex("M-Shwari balance is Ksh[\\d,.]+", RegexOption.IGNORE_CASE),
                                    "M-Shwari balance is ********"
                                )
                                .replace(
                                    Regex("(New )?M-PESA balance (is|:) Ksh[\\d,.]+", RegexOption.IGNORE_CASE),
                                    "M-PESA balance: ********"
                                )

                            Text(
                                text = sanitizedMessage,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                lineHeight = 18.sp,
                                textAlign = TextAlign.Left
                            )
                        }
                }
            }
        }*/


        /*BottomSheetFa(
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
                        *//*NormalText(
                            text = selectedMessage,
                            textAlign = TextAlign.Left
                        )*//*
                        VerticalSpacer(10)
                        *//*if (transaction.transactionType == TransactionType.SEND_MONEY) {
                            OutlineButtonFa(
                                text = "Reverse Transaction",
                                onClick = {
                                    //TODO
                                    Toast.makeText(context, "Coming soon...", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }*//*
                    }
                }
            }
        }*/

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


