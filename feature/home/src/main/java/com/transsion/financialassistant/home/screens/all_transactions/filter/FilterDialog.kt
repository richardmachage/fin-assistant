package com.transsion.financialassistant.home.screens.all_transactions.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.transsion.financialassistant.data.models.TransactionCategory
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.buttons.OutlineButtonFa
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.components.texts.TitleText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingLarge

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TransactionFilterDialog(
    filter: FilterState,//viewModel: FilterViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onApply: (FilterState) -> Unit
) {
    var filterState by remember { mutableStateOf(filter) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(15),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingLarge)
                    .verticalScroll(rememberScrollState())
            ) {

                // Filter Source Radio Buttons
                TitleText(
                    text = stringResource(R.string.filter_by_source)
                )
                VerticalSpacer(10)
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TransactionCategory.entries.forEach { source ->
                        RadioButtonWithLabel(
                            text = source.description,
                            selected = filterState.source == source,
                            onClick = {
                                filterState = filterState.copy(source = source)
                                //viewModel.onSourceChanged(source)
                            }
                        )
                    }
                }

                VerticalSpacer(16)

                // Filter by Period
                TitleText(
                    text = stringResource(R.string.filter_by_period)
                )

                FlowRow {
                    FilterPeriod.entries.forEach { period ->
                        FilterChip(
                            shape = RoundedCornerShape(15),
                            selected = filterState.period == period,
                            onClick = {
                                filterState = filterState.copy(period = period)
                            },
                            label = { NormalText(text = period.label) },
                            colors = FilterChipDefaults.filterChipColors(
                                labelColor = MaterialTheme.colorScheme.onBackground,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
                                selectedContainerColor = FAColors.green,
                                containerColor = MaterialTheme.colorScheme.background

                            )
                        )
                        HorizontalSpacer(4)
                    }
                }

                VerticalSpacer(24)

                // Apply and Discard Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlineButtonFa(
                        text = stringResource(R.string.discard),
                        onClick = {
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    )

                    HorizontalSpacer(8)

                    FilledButtonFa(
                        text = stringResource(R.string.apply),
                        onClick = {
                            onApply(filterState)
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun RadioButtonWithLabel(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = RadioButtonDefaults.colors(
                selectedColor = FAColors.green,
                unselectedColor = MaterialTheme.colorScheme.onBackground
            )
        ) // Null because Row is clickable
        NormalText(text = text)
    }
}
