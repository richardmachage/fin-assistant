package com.transsion.financialassistant.insights.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun InsightCategoryCard(
    tittle: String = "Shopping",
    amount: String = "1,452.00",
    onClick: () -> Unit = {}
) {

    Row {
        repeat(2) {
            HorizontalSpacer(10)
            ElevatedCard(
                modifier = Modifier.weight(1f),//.padding(paddingMedium),
                shape = RoundedCornerShape(20),
            ) {
                Column(
                    modifier = Modifier
                        .height(120.dp)
                        .padding(paddingMedium)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        //tittle
                        NormalText(text = tittle, fontSize = 13.sp)

                        //button
                        IconButton(
                            onClick = {
                                onClick()
                            }
                        ) {
                            Icon(
                                painter = painterResource(com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined),
                                contentDescription = null
                            )
                        }
                    }

                    VerticalSpacer(10)


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FaintText(
                            modifier = Modifier.padding(bottom = paddingSmall),
                            text = stringResource(com.transsion.financialassistant.presentation.R.string.kes),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                        HorizontalSpacer(3)
                        //Amount
                        TitleText(text = amount)

                    }
                    VerticalSpacer(10)

                    IconButton(
                        modifier = Modifier.clip(CircleShape),
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = FAColors.greenOverlay
                        ),
                        onClick =
                            onClick
                    ) {
                        Icon(
                            Icons.Default.Build,
                            contentDescription = null
                        )
                    }

                }
            }
        }
    }
}


@Composable
fun DonationCard(
    amount: String = "34,990.90",
    currency: String = "KES",
    title: String = "Gifts & Donations",
    modifier: Modifier = Modifier,
    icon: Painter // Pass the icon in to make it more reusable
) {
    Card(
        modifier = modifier
            //  .fillMaxWidth()
            .aspectRatio(1f), // Keep it square-ish but flexible
        shape = RoundedCornerShape(10),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
            //.padding(16.dp)
        )
        {

            // 🔼 Top Row
            Row(
                modifier = Modifier.align(Alignment.TopStart),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE4F8EF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Go",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            // 💰 Centered Amount
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = currency,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Text(
                    text = amount,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.DarkGray
                )
            }

            // 🎁 Bottom Left Icon
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 10.dp)
                    .align(Alignment.BottomStart)
                    .clip(CircleShape)
                    .background(FAColors.GrayBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = "Category Icon",
                    tint = Color.Gray,

                    )
            }
        }
    }
}


@PreviewLightDark
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun prev() {
    Scaffold {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(2),
        ) {
            items(10) {
                DonationCard(icon = painterResource(com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined))
            }
        }

    }
}