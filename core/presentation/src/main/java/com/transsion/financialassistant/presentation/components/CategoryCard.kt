package com.transsion.financialassistant.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.transsion.financialassistant.presentation.components.buttons.IconButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall


@Preview
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    title: String = "Pochi la Biashara",
    amount: String = "6,900.90",
    @DrawableRes icon: Int = com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined,
    onClick: () -> Unit = {},
    showTopIcon: Boolean = false
) {
    ElevatedCard(
        shape = RoundedCornerShape(10),
        modifier = modifier
            .clickable(
                enabled = showTopIcon,
                onClick = { onClick() }
            ),
    ) {

        Column {
            // Top Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NormalText(
                    text = title,
                    textAlign = TextAlign.Left
                )

                if (showTopIcon) {
                    Box(
                        modifier = Modifier
                            .width(35.dp)
                            .clip(RoundedCornerShape(40))
                            .background(FAColors.faintText.copy(alpha = 0.3f))
                            .clickable {
                                onClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(com.transsion.financialassistant.presentation.R.drawable.weui_arrow_outlined), //Icons.Default.KeyboardArrowRight,
                            contentDescription = "Go",
                        )
                    }
                }
            }

            // Centered Amount
            Column(
                modifier = Modifier
                    //    .align(Alignment.Center)
                    .padding(paddingSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(com.transsion.financialassistant.presentation.R.string.kes),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )

                    TitleText(
                        text = " $amount",
                    )
                }

            }


            IconButtonFa(
                // modifier = Modifier.align(Alignment.BottomStart),
                icon = painterResource(icon),
                colors = IconButtonDefaults.iconButtonColors().copy(
                    containerColor = FAColors.faintText.copy(0.3F)
                ),
                onClick = {
                    onClick()
                }
            )
        }

    }

}