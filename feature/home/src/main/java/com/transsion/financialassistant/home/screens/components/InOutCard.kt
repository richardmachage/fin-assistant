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
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun InOutCard(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(paddingMedium),
    moneyIn: String = "236,900.60",
    moneyOut: String = "177,500.90",
    transactionsIn: String = "14",
    transactionsOut: String = "256",
    onHideBalance: () -> Unit,
    hide: Boolean,
    ) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(15),

        ) {
        VerticalSpacer(10)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //money In
            InOutCardCategoryCard(
                category = TransactionCategory.IN,
                amount = moneyIn,
                transactions = transactionsIn,
                hide = hide

            )

            Column(
                modifier = Modifier.align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //divider
                VerticalDivider(
                    Modifier
                        .height(50.dp)
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

            //money out
            InOutCardCategoryCard(
                category = TransactionCategory.OUT,
                amount = moneyOut,
                transactions = transactionsOut,
                hide = hide
            )

        }
        VerticalSpacer(10)

    }
}

@Composable
fun InOutCardCategoryCard(
    modifier: Modifier = Modifier,
    category: TransactionCategory = TransactionCategory.IN,
    amount: String = "236,900.60",
    transactions: String = "14",
    hide: Boolean,
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
                modifier = if (hide) Modifier.blur(10.dp) else Modifier,
                text = amount
            )

        }
        VerticalSpacer(5)
        Row(verticalAlignment = Alignment.CenterVertically) {
            val transaction =
                stringResource(com.transsion.financialassistant.presentation.R.string.transactions)
            NormalText(
                text = "$transactions $transaction",
            )
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InOutCardPreview() {
    FinancialAssistantTheme {
        InOutCard(onHideBalance = {}, hide = true)

    }
}