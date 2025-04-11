package com.transsion.financialassistant.home.screens.all_transactions.filter_values.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.transsion.financialassistant.home.R
import com.transsion.financialassistant.home.model.all_transactions.FilterCategory
import com.transsion.financialassistant.home.model.all_transactions.FilterPeriod
import com.transsion.financialassistant.home.model.all_transactions.FilterSource
import com.transsion.financialassistant.home.model.all_transactions.FilterState
import com.transsion.financialassistant.home.screens.all_transactions.filter_values.viewmodel.FilterViewModel
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
    viewModel: FilterViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onApply: (FilterState) -> Unit
){
    val filterState by viewModel.filterState.collectAsStateWithLifecycle()

    Dialog(onDismissRequest = onDismiss) {
        Surface (
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background,
            tonalElevation = 8.dp
        ){
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
                Row {
                    RadioButtonWithLabel(stringResource(R.string.money_in), filterState.source == FilterSource.MONEY_IN) {
                        viewModel.onSourceChanged(FilterSource.MONEY_IN)
                    }
                   HorizontalSpacer(8)
                    RadioButtonWithLabel(stringResource(R.string.money_out), filterState.source == FilterSource.MONEY_OUT) {
                        viewModel.onSourceChanged(FilterSource.MONEY_OUT)
                    }
                }

                VerticalSpacer(16)

                // Filter by Period
                TitleText(
                    text = stringResource(R.string.filter_by_period)
                )

                FlowRow() {
                    FilterPeriod.entries.forEach { period ->
                        FilterChip(
                            shape = RoundedCornerShape(16.dp),
                            selected = filterState.period == period,
                            onClick = { viewModel.onPeriodChanged(period) },
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
                // Filter by Category
                /* TitleText(
                  text = stringResource(R.string.filter_by_category)
              )

            FlowRow (){
                  FilterCategory.entries.forEach { category ->
                      FilterChip(
                          selected = category in filterState.selectedCategories,
                          onClick = { viewModel.toggleCategory(category) },
                          label = { NormalText(text = category.label) }
                      )
                      HorizontalSpacer(4)
                  }
              }*/
                VerticalSpacer(24)

                // Apply and Discard Button

                Row (
                   //horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    OutlineButtonFa(
                        text = stringResource(R.string.discard),
                        onClick = {
                            viewModel.resetFilters()
                            onDismiss()
                        },
                        modifier = Modifier.weight(1f)
                    )

                    HorizontalSpacer(8)

                    FilledButtonFa(
                        text = stringResource(R.string.apply),
                        onClick = {
                            onApply(viewModel.applyFilters())
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
