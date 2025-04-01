package com.transsion.financialassistant.insights.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun InsightCategoryCard(
    modifier: Modifier = Modifier,
    title: String = "Shopping",
    amount: String = "14,520.00",
    onClick: () -> Unit = {},
    categoryIcon: Painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined)
) {
    ElevatedCard(
        modifier = modifier
            .aspectRatio(1f), // Keep it square-ish but flexible
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            // Top Row
            Row(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(paddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalText(
                    modifier = Modifier.weight(1f),
                    text = title,
                    textAlign = TextAlign.Left
                )

                Box(
                    modifier = Modifier
                        .width(35.dp)
                        .clip(RoundedCornerShape(40))
                        .background(FAColors.faintText.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined), //Icons.Default.KeyboardArrowRight,
                        contentDescription = "Go",
                    )
                }
            }

            // Centered Amount
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(paddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(com.transsion.financialassistant.presentation.R.string.kes),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                BigTittleText(
                    text = amount
                )

            }

            IconButtonFa(
                modifier = Modifier.align(Alignment.BottomStart),
                icon = categoryIcon,
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = FAColors.faintText.copy(0.3F)
                )
            )

        }
    }

}


//@PreviewLightDark
@Preview(showBackground = true)
@Composable
fun Prev() {
    FinancialAssistantTheme {
        Scaffold {
            LazyVerticalGrid(
                modifier = Modifier.padding(it),
                columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
            ) {
                items(10) {
                    InsightCategoryCard()
                }
            }
        }
    }
}