package com.transsion.financialassistant.insights.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme

@Composable
fun MoneyToggle(
    modifier: Modifier = Modifier,
    selectedOption: TransactionCategory,
    onOptionSelected: (TransactionCategory) -> Unit
) {
    val options = TransactionCategory.entries

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            val backgroundColor = if (isSelected) FAColors.green else Color.Transparent
            val contentColor = if (isSelected) Color.White else {
                if (isSystemInDarkTheme()) Color.White else Color.Black
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(backgroundColor)
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                NormalText(
                    text = option.description,
                    textColor = contentColor
                )

            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SegementendInOutButtonPrev() {
    //SegmentendInOutButton()
    var selectedOption by remember { mutableStateOf(TransactionCategory.IN) }

    FinancialAssistantTheme(darkTheme = true) {
        MoneyToggle(
            selectedOption = selectedOption,
            onOptionSelected = {
                selectedOption = it
            }
        )
    }
}