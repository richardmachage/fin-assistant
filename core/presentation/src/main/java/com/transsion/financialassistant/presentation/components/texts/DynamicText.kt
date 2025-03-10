package com.transsion.financialassistant.presentation.components.texts

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.transsion.financialassistant.presentation.theme.FAColors

@Composable
fun DynamicText(
    @StringRes stringId: Int,
    dynamicValue: String,
    dynamicColor: Color? = MaterialTheme.colorScheme.onBackground,
    dynamicTextDecoration: TextDecoration? = null,
    onClick: (() -> Unit)? = null
) {
    val text = stringResource(stringId, dynamicValue)

    val annotatedString = buildAnnotatedString {
        val valueIndex = text.indexOf(dynamicValue)
        append(text)

        if (valueIndex != -1) {

            addStyle(
                style = SpanStyle(
                    color = dynamicColor ?: Color.Unspecified,
                    textDecoration = dynamicTextDecoration
                ),
                start = valueIndex,
                end = valueIndex + dynamicValue.length
            )

            if (onClick != null) {
                addStringAnnotation(
                    tag = "dynamic_value",
                    annotation = dynamicValue,
                    start = valueIndex,
                    end = valueIndex + dynamicValue.length
                )
            }
        }
    }

    Text(
        annotatedString,
        color = FAColors.faintText,
        modifier = Modifier.clickable {
            onClick?.invoke()
        },
        textAlign = TextAlign.Center
    )
}