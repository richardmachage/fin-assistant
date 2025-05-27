package com.transsion.financialassistant.search.screens.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.search.model.RecentSearchQuery

@Composable
fun RecentSearchUi(
    modifier: Modifier = Modifier,
    item: RecentSearchQuery = RecentSearchQuery(id = 1L, query = "Pamela Makhoka"),
    onDelete: () -> Unit = {},
    onClick: () -> Unit = {}
) {

    Row(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(start = paddingMedium, end = paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NormalText(text = item.query, textAlign = TextAlign.Left)

        IconButton(
            onClick = { onDelete() }
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Delete Icon"
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PrevRe() {
    FinancialAssistantTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(7) {
                RecentSearchUi()
            }
        }
    }
}
