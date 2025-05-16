package com.transsion.financialassistant.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun KesAmount(
    modifier: Modifier = Modifier,
    amount: String,
    textSize: TextUnit = 16.sp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        FaintText(
            modifier = Modifier.padding(bottom = paddingSmall),
            text = stringResource(com.transsion.financialassistant.presentation.R.string.kes),
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold
        )
        HorizontalSpacer(3)
        //Amount
        TitleText(text = amount, fontSize = textSize)

    }
}