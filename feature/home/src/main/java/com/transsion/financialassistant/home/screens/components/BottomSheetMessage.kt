package com.transsion.financialassistant.home.screens.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.views.personal.UnifiedTransactionPersonal
import com.transsion.financialassistant.presentation.components.bottom_sheets.BottomSheetFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import java.util.Locale

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
            transaction.let { transaction ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingLarge),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {

                    TitleText(
                        text = transaction.transactionType.description,
                        fontSize = 16.sp,
                    )
                    VerticalSpacer(20)

                    // Centered Amount
                    BigTittleText(
                        text = "KES ${transaction.amount}",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )

                    VerticalSpacer(20)

                    // Receiver Name
                    transaction.name?.let { receiverName ->
                        NormalText(
                            text = "To: ${receiverName.uppercase(Locale.ROOT)}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                        )
                        VerticalSpacer(10)
                    }

                    // Message without M-PESA balance
                    val sanitizedMessage = selectedMessage
                        .replace(
                            Regex("M-Shwari balance is Ksh[\\d,.]+", RegexOption.IGNORE_CASE),
                            "M-Shwari balance is ********"
                        )
                        .replace(
                            Regex("(New )?M-PESA balance (is|:) Ksh[\\d,.]+", RegexOption.IGNORE_CASE),
                            "M-PESA balance: ********"
                        )

                    Text(
                        text = selectedMessage,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        lineHeight = 18.sp,
                        textAlign = TextAlign.Left
                    )
                }
            }
        }
    }
}

