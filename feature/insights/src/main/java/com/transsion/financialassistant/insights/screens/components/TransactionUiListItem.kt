package com.transsion.financialassistant.insights.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.utils.formatAsCurrency
import com.transsion.financialassistant.insights.model.TransactionUi
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.theme.colorFor
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall


@Composable
fun TransactionUiListItem(
    modifier: Modifier = Modifier,
    transactionUi: TransactionUi = TransactionUi(
        code = "GFARYHNGF",
        title = "NAIVAS",
        type = TransactionType.BUY_GOODS,
        amount = "50.00",
        inOrOut = TransactionCategory.OUT,
        dateAndTime = "Jan 12, 9:47 AM"
    )
) {

    val descr = if (transactionUi.type == TransactionType.MOVE_TO_POCHI) {
        when (transactionUi.inOrOut) {
            TransactionCategory.IN -> "Transfer From MPESA"
            TransactionCategory.OUT -> "Transfer To POCHI"
        }
    } else if (transactionUi.type == TransactionType.SEND_MONEY && transactionUi.title.lowercase() == "ziidi") {
        "Sent to Ziidi"
    } else {
        transactionUi.type.description
    }
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
                text = transactionUi.title.uppercase(),
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


@Composable
fun TransactionUiListItem(
    modifier: Modifier = Modifier,
    transactionUi: TransactionUi = TransactionUi(
        code = "GFARYHNGF",
        title = "Naivas SuperMarket special",
        type = TransactionType.SEND_POCHI,
        amount = "50.00",
        inOrOut = TransactionCategory.OUT,
        dateAndTime = "Jan 12, 9:47 AM"
    ),
    onClick: () -> Unit = {}
) {

    val descr = if (transactionUi.type == TransactionType.MOVE_TO_POCHI) {
        when (transactionUi.inOrOut) {
            TransactionCategory.IN -> "Transfer From MPESA"
            TransactionCategory.OUT -> "Transfer To POCHI"
        }
    } else if (transactionUi.type == TransactionType.SEND_MONEY && transactionUi.title.lowercase() == "ziidi") {
        "Sent to Ziidi"
    } else {
        transactionUi.type.description
    }

    VerticalSpacer(5)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(paddingSmall)
            .clip(RoundedCornerShape(20))
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    //.padding(paddingSmall)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color = colorFor(title = transactionUi.title)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = transactionUi.title.first().toString().uppercase(),
                    fontSize = 22.sp,
                    color = Color.White.copy(0.9f)
                )

                /*TitleText(
                    // modifier = Modifier.padding(paddingSmall),
                    text = transactionUi.title.first().toString().uppercase(),
                    fontSize = 24.sp,
                    textColor = Color.White.copy(0.9f)
                )*/
            }
            HorizontalSpacer(4)

            Column(
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                // VerticalSpacer(5)
                //tittle
                Text(
                    text = transactionUi.title.uppercase(),
                    overflow = TextOverflow.Clip,
                    color = MaterialTheme.colorScheme.onBackground,//.copy(alpha = 0.7f),
                    maxLines = 1,
                    // fontWeight = FontWeight.Bold
                )
                VerticalSpacer(4)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 6.dp,
                            end = 6.dp,
                            top = 4.dp,
                            bottom = 4.dp
                        ),
                        text = descr,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.65f),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }
            }
        }
        Column {
            VerticalSpacer(4)

            NormalText(
                modifier = Modifier.align(Alignment.End),
                text = when (transactionUi.inOrOut) {
                    TransactionCategory.IN -> "+KES ${transactionUi.amount.formatAsCurrency()}"
                    TransactionCategory.OUT -> "-KES ${transactionUi.amount.formatAsCurrency()}"
                },
                textColor = when (transactionUi.inOrOut) {
                    TransactionCategory.IN -> FAColors.green.copy(alpha = 0.8F)
                    TransactionCategory.OUT -> Color.Red.copy(alpha = 0.7f)
                },
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Right
            )
            VerticalSpacer(6)
            NormalText(
                text = transactionUi.dateAndTime,
                fontSize = 11.sp,
                textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )
        }
    }
    VerticalSpacer(5)
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionUiListItemPrev() {
    FinancialAssistantTheme {
        TransactionUiListItem()
    }
}