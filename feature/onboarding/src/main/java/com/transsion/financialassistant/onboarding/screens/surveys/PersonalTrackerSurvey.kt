package com.transsion.financialassistant.onboarding.screens.surveys

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.R.drawable
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FinancialAssistantTheme
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PersonalTrackerSurvey(
    navController: NavController
){
    val categories = listOf(
        stringResource(R.string.baby_clothes) to drawable.baby_02,
        stringResource(R.string.gifts_donations) to drawable.gifts___donations,
        stringResource(R.string.bills_utilities) to drawable.bulb,
        stringResource(R.string.clothing) to drawable.clothing,
        stringResource(R.string.groceries) to drawable.groceries,
        stringResource(R.string.rent) to drawable.rent,
        stringResource(R.string.savings) to drawable.savings,
        stringResource(R.string.transaction_costs) to drawable.transaction,
        stringResource(R.string.entertainment) to drawable.entertainment,
        stringResource(R.string.healthcare) to drawable.healtcare
    )
    val selected by remember { mutableStateOf(false) }

    val selectedItems = remember { mutableStateListOf<String>() }

    Surface {
        val paddingValues = WindowInsets.statusBars.asPaddingValues()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = paddingMedium),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            modifier = Modifier.padding(paddingSmall)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        HorizontalSpacer(8)
                        BigTittleText(
                            text = stringResource(R.string.money_expenditure_personal),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = paddingSmall)
                        )
                    }

                    VerticalSpacer(16)
                    FaintText(
                        text = stringResource(R.string.money_out_categories),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(start = paddingSmall)
                    )
                    VerticalSpacer(16)
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        categories.forEach { (category, iconRes) ->
                            val isSelected = selectedItems.contains(category)
                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    if (isSelected) selectedItems.remove(category)
                                    else selectedItems.add(category)
                                },
                                label = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            painter = painterResource(id = iconRes),
                                            contentDescription = "category"
                                        )
                                        HorizontalSpacer(4)
                                        NormalText(text = category)
                                    }

                                },
//                              leadingIcon = if (isSelected){
//                                  { Icon(
//                                      imageVector = Icons.Filled.Check,
//                                      contentDescription = "Selected")
//                                  }
//                              } else null,
                                modifier = Modifier.padding(paddingSmall)
                            )
                        }
                    }
                }
                    FilledButtonFa(
                        text = stringResource(R.string.next_btn),
                        onClick = {},
                        enabled = if (selectedItems.isNotEmpty()) true else false,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .align(Alignment.BottomCenter)
                            .padding(WindowInsets.navigationBars.asPaddingValues())
                    )
                }
            }
        }
    }

@Preview
@Composable
fun PersonalTrackerSurveyPreview(){
    FinancialAssistantTheme {
      // PersonalTrackerSurvey()
    }
}