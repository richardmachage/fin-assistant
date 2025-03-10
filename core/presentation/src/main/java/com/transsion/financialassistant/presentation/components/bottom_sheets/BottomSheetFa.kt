package com.transsion.financialassistant.presentation.components.bottom_sheets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import com.transsion.financialassistant.presentation.theme.FAColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFa(
    isSheetOpen: Boolean = false,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onDismiss: () -> Unit,
    content: @Composable () -> Unit = {},
) {

    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = FAColors.GrayBackground
        ) {
            content()
        }
    }

}