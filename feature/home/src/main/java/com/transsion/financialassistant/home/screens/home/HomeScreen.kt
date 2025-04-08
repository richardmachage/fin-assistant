package com.transsion.financialassistant.home.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.TransactionUi
import com.transsion.financialassistant.home.screens.components.InsightCateToggleSegmentedButton
import com.transsion.financialassistant.home.screens.components.MpesaBalanceCard
import com.transsion.financialassistant.home.screens.components.MyBudgetsCard
import com.transsion.financialassistant.home.screens.components.TransactionUiListItem
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.ClickableText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp

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
                        NormalText(text = "Good Morning,")
                        TitleText(text = "Katei")
                    }
                },
                actions = {
                    //search
                    IconButtonFa(
                        icon = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.search),
                        colors = colors(),
                        onClick = {
                            //TODO
                        }
                    )
                    //more

                    IconButtonFa(
                        icon = Icons.Default.MoreVert,
                        colors = colors(),
                        onClick = {
                            //TODO
                        }
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
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
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

            ) {

            MpesaBalanceCard(
                balance = "1,900.0"
            )

            var selectedCat by remember { mutableStateOf(InsightCategory.PERSONAL) }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingSmall),
                contentAlignment = Alignment.Center
            ) {
                InsightCateToggleSegmentedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    selectedOption = selectedCat,
                    onOptionSelected = {
                        selectedCat = it
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
                val allTransactions = 214
                ClickableText(text = "$viewAll ($allTransactions)", onClick = {})

            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium)
                    .heightIn(max = (screenHeight / 4).dp),

                ) {
                items(3) {
                    TransactionUiListItem(
                        transactionUi = TransactionUi(
                            title = "NAIVAS",
                            type = if (it % 2 != 0) TransactionType.SEND_POCHI else TransactionType.BUY_GOODS,
                            amount = "50.00",
                            inOrOut = if (it % 2 != 0) TransactionCategory.OUT else TransactionCategory.IN,
                            dateAndTime = "Jan 12, 9:47 AM"
                        )
                    )
                }
            }

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