package com.transsion.financialassistant.home.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.home.model.BudgetInfo
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.utils.paddingMedium


@PreviewLightDark()
@Preview(showSystemUi = true)
@Composable
fun MyBudgetsCard(
    budgetInfo: BudgetInfo = BudgetInfo(
        category = "Budgets",
        recurrence = "Recurrent",
        title = "Household Budget",
        currentAmount = 37785.00,
        limitAmount = 40000.00,
        message = "You’ve kept the discipline so far. Everything is good."
    )
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = paddingMedium),
        shape = RoundedCornerShape(10),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingMedium)
        ) {
            BudgetHeader(category = budgetInfo.category, recurrence = budgetInfo.recurrence)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = budgetInfo.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                NormalText(text = "KES %.2f".format(budgetInfo.currentAmount))
                NormalText(text = "KES %.2f".format(budgetInfo.limitAmount))

            }
            Spacer(modifier = Modifier.height(8.dp))
            BudgetProgressBar(current = budgetInfo.currentAmount, limit = budgetInfo.limitAmount)
            Spacer(modifier = Modifier.height(12.dp))
            BudgetFooterMessage(message = budgetInfo.message)
        }
    }
}


@Composable
fun BudgetFooterMessage(message: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Default.CheckCircle, // You can change this to something more fitting
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}


@Composable
fun BudgetAmountRow(current: Double, limit: Double) {

}


@Composable
fun BudgetHeader(category: String, recurrence: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.coins_01), // Replace with correct icon if needed
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(24.dp)
                .background(Color(0xFF26A69A), shape = CircleShape)
                .padding(4.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = category, style = MaterialTheme.typography.labelMedium)
            Text(text = recurrence, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
    }
}

@Composable
fun BudgetProgressBar(current: Double, limit: Double) {
    val progress = (current / limit).coerceIn(0.0, 1.0).toFloat()
    Box(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = Color(0xFF26A69A),
            trackColor = Color.LightGray.copy(alpha = 0.3f),
            drawStopIndicator = {}
        )
    }
}
