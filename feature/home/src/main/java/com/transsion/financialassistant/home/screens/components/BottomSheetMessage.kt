package com.transsion.financialassistant.home.screens.components

import android.widget.Toast
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetFaMessage(
    modifier: Modifier = Modifier,
    transaction: UnifiedTransactionPersonal,
    message: String,
    onDismiss: () -> Unit
) {
    BottomSheetFa(
        modifier = modifier,
        isSheetOpen = true,
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = paddingLarge)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Top Left: Transaction Type
            BigTittleText(
                text = transaction.transactionType.description,
                modifier = Modifier.align(Alignment.Start)
            )

            VerticalSpacer(12)

            // Center: Amount
            TitleText(
                text = transaction.amount.toString(),
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            VerticalSpacer(12)

            // Receiver Name and Message
            Text(
                text = "To: ${transaction.name}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            VerticalSpacer(6)

            Text(
                text = message.replace(Regex("""(\b(?:balance|amount)\s*[:=]?\s*)(\d+[,.]?\d*)"""), "$1****"),
                fontSize = 14.sp,
                lineHeight = 18.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            VerticalSpacer(16)

            if (transaction.transactionType == TransactionType.SEND_MONEY) {
                OutlineButtonFa(
                    text = "Reverse Transaction",
                    onClick = {
                       /* Toast.makeText(LocalContext.current, "Coming soon...", Toast.LENGTH_SHORT)
                            .show()*/
                    }
                )
            }
        }
    }
}

