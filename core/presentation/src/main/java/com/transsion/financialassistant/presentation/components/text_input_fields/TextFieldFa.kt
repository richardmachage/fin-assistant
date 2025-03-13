package com.transsion.financialassistant.presentation.components.text_input_fields

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.theme.FAColors

@Composable
fun TextFieldFa(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = label?.let { { FaintText(text = it) } },
        placeholder = placeholder.let { { FaintText(text = it) } },
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(50),
        maxLines = maxLines,
        colors = TextFieldDefaults.colors().copy(
            unfocusedContainerColor = FAColors.GrayBackground,
            focusedContainerColor = FAColors.GrayBackground,
            focusedTextColor = FAColors.black,
            unfocusedTextColor = FAColors.black,
            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = FAColors.green,
            unfocusedPlaceholderColor = Color.Black
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
    )
}