package com.transsion.financialassistant.search.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.presentation.theme.colorFor
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall
import com.transsion.financialassistant.search.model.ListItem

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListItemUI(
    modifier: Modifier = Modifier,
    transactionUi: ListItem = ListItem(
        transactionCode = "GFARYHNGF",
        title = "Naivas SuperMarket special",
        transactionType = TransactionType.SEND_POCHI,
        transactionCategory = TransactionCategory.OUT,
    ),
    onClick: () -> Unit = {}
) {


    VerticalSpacer(5)
    Row(
        modifier = modifier
            .padding(paddingSmall)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
            }

            VerticalSpacer(3)

            //Name
            Text(
                modifier = Modifier.width(90.dp),
                text = transactionUi.title.uppercase(),
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground,//.copy(alpha = 0.7f),
                maxLines = 1,
                textAlign = TextAlign.Center,
                fontSize = 11.sp
            )
        }
    }
}



