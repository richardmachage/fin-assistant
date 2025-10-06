package com.transsion.financialassistant.home.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.data.models.InsightCategory
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.presentation.components.KesAmount
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MpesaBalanceCard(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(paddingMedium),
    balance: String = "236,900.60",
    moneyIn: String = "236,900.60",
    moneyOut: String = "177,500.90",
    insightCategory: InsightCategory = InsightCategory.PERSONAL,
    hide: Boolean,
    onHideBalance: () -> Unit
) {

    ElevatedCard(
        modifier = modifier,//.height(215.dp),
        shape = RoundedCornerShape(10),
    ) {
        VerticalSpacer(10)

        // val today = stringResource(R.string.today)


        //balance
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (insightCategory) {
                InsightCategory.PERSONAL -> Text(
                    text = stringResource(R.string.mpesa_balance),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),

                    )

                InsightCategory.BUSINESS -> Text(
                    text = stringResource(R.string.business_balance),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),

                    )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                KesAmount(
                    modifier = if (hide) Modifier.blur(10.dp) else Modifier,
                    amount = balance,
                    textSize = 22.sp
                )
                IconButton(
                    onClick = {
                        onHideBalance()
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            if (hide) com.transsion.financialassistant.presentation.R.drawable.solar_eye_outline else com.transsion.financialassistant.presentation.R.drawable.view_off_slash
                        ),
                        contentDescription = "eye",
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //money In
            InOutCardCategory(
                category = TransactionCategory.IN,
                amount = moneyIn,
                hide = hide
                // transactions = transactionsIn
            )
            //divider
            VerticalDivider(
                Modifier
                    .height(50.dp)
                    .align(Alignment.CenterVertically),
                thickness = 0.5.dp
            )

            //money out
            InOutCardCategory(
                category = TransactionCategory.OUT,
                amount = moneyOut,
                hide = hide
                // transactions = transactionsOut
            )
        }
        VerticalSpacer(10)

    }
}

@Composable
fun InOutCardCategory(
    modifier: Modifier = Modifier,
    category: TransactionCategory = TransactionCategory.IN,
    amount: String = "236,900.60",
    transactions: String? = null,
    hide: Boolean = false
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            when (category) {
                TransactionCategory.IN -> {
                    Icon(
                        painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.arrowdownright),
                        contentDescription = "Money In",
                        tint = FAColors.green
                    )
                    // HorizontalSpacer(5)
                    FaintText(
                        text = stringResource(com.transsion.financialassistant.presentation.R.string.money_in)
                    )
                }

                TransactionCategory.OUT -> {
                    Icon(
                        painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.arrowupright),
                        contentDescription = "Money Out",
                        tint = Color.Red
                    )
                    // HorizontalSpacer(5)
                    FaintText(text = stringResource(com.transsion.financialassistant.presentation.R.string.money_out))
                }
            }
        }

        VerticalSpacer(5)
        Row(verticalAlignment = Alignment.Bottom) {
            FaintText(
                modifier = Modifier.padding(bottom = paddingSmall),
                text = stringResource(com.transsion.financialassistant.presentation.R.string.kes),
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold
            )
            HorizontalSpacer(3)
            //Amount
            TitleText(
                text = amount,
                modifier = if (hide) Modifier.blur(10.dp) else Modifier
            )

        }
        VerticalSpacer(5)
        transactions?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val transaction =
                    stringResource(com.transsion.financialassistant.presentation.R.string.transactions)
                NormalText(text = "$transactions $transaction")
            }
        }
    }

}


fun getAmOrPm(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
    return LocalTime.now().format(formatter)
}