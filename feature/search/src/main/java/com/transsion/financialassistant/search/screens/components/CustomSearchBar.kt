package com.transsion.financialassistant.search.screens.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    onFocusChanged: (Boolean) -> Unit,
) {

    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier
            .onFocusChanged { focusState ->
                val nowFocused = focusState.isFocused
                if (nowFocused != isFocused) {
                    onFocusChanged(nowFocused)
                    isFocused = nowFocused
                }
            },
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text("Search by name, code, type") },
        leadingIcon = {
            if (query.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            }
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Search Icon"
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(50),
        colors = OutlinedTextFieldDefaults.colors().copy(
            focusedIndicatorColor = FAColors.green
        )
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomSearchBarPreview() {
    FinancialAssistantTheme {
        CustomSearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = "",
            onQueryChanged = {},
            onFocusChanged = {},
        )
    }
}