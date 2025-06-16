package com.transsion.financialassistant.home.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.presentation.components.bottom_sheets.BottomSheetFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFaMessage(
    modifier: Modifier = Modifier,
    showMessageBottomSheet: Boolean,
    selectedMessage: String,
    transaction: UnifiedTransactionPersonal,
    onDismiss: () -> Unit
) {
    BottomSheetFa(
        modifier = modifier,
        isSheetOpen = showMessageBottomSheet,
        onDismiss = {
            onDismiss()
        }
    ) {

        if (selectedMessage.isNotBlank()) {
            var hideBalance by remember { mutableStateOf(true) }

            transaction.let { transaction ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingLarge),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    // Receiver Name
                    transaction.name?.let { receiverName ->
                        TitleText(
                            text = receiverName.uppercase(),
                            fontSize = 16.sp,
                        )
                        // VerticalSpacer(10)
                    }

                    // Centered Amount
                    BigTittleText(
                        text = "KES ${transaction.amount.toInt()}",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 28.sp,
                        fontWeight = FontWeight(500)
                    )

                    VerticalSpacer(10)

                    TitleText(
                        text = transaction.transactionType.description,
                        fontSize = 16.sp,
                    )
                    VerticalSpacer(10)

                    val hiddenBalanceMessage = selectedMessage.replace(
                        regex = "is Ksh([\\d,]+\\.?\\d{0,2})(.)?".toRegex(RegexOption.IGNORE_CASE),
                        replacement = "is Ksh ******"
                    )

                    Text(
                        text = if (hideBalance) hiddenBalanceMessage else selectedMessage,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Left
                    )
                    VerticalSpacer(10)
                    OutlineButtonFa(
                        text = if (hideBalance) "Show Balance" else "Hide Balance",
                        onClick = {
                            hideBalance = !hideBalance
                        }
                    )
                }
            }
        }
    }
}