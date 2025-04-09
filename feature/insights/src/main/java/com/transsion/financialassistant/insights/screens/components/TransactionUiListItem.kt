package com.transsion.financialassistant.insights.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.insights.model.TransactionUi
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall


@Composable
fun TransactionUiListItem(
    modifier: Modifier = Modifier,
    transactionUi: TransactionUi = TransactionUi(
        title = "NAIVAS",
        type = TransactionType.BUY_GOODS,
        amount = "50.00",
        inOrOut = TransactionCategory.OUT,
        dateAndTime = "Jan 12, 9:47 AM"
    )
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingSmall),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    //.padding(paddingSmall)
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = when (transactionUi.inOrOut) {
                            TransactionCategory.IN -> FAColors.green
                            TransactionCategory.OUT -> Color.Red
                        },
                        shape = CircleShape

                    ),
                contentAlignment = Alignment.Center
            ) {
                TitleText(
                    // modifier = Modifier.padding(paddingSmall),
                    text = transactionUi.title.first().toString().uppercase(),
                    fontSize = 30.sp
                )
            }
            HorizontalSpacer(5)

            //tittle

            Text(
                modifier = Modifier.fillMaxWidth(0.5f),
                text = transactionUi.title,
                overflow = TextOverflow.Clip,
                color = MaterialTheme.colorScheme.onBackground,
            )


        }

        Row {
            Column {
                NormalText(
                    text = when (transactionUi.inOrOut) {
                        TransactionCategory.IN -> "+ KES ${transactionUi.amount}"
                        TransactionCategory.OUT -> "- KES ${transactionUi.amount}"
                    },
                    textColor = when (transactionUi.inOrOut) {
                        TransactionCategory.IN -> FAColors.green
                        TransactionCategory.OUT -> Color.Red
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                NormalText(text = transactionUi.dateAndTime)
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionUiListItemPrev() {
    FinancialAssistantTheme {
        TransactionUiListItem()
    }
}