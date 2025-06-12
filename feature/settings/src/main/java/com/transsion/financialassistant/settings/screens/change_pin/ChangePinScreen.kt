package com.transsion.financialassistant.settings.screens.change_pin

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.transsion.financialassistant.presentation.components.buttons.BackButton
import com.transsion.financialassistant.presentation.components.buttons.FilledButtonFa
import com.transsion.financialassistant.presentation.components.text_input_fields.PasswordTextFieldFa
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.paddingLarge
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingMediumLarge
import com.transsion.financialassistant.settings.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePinScreen(
    navController: NavController = rememberNavController(),
    viewModel: ChangePinViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val isPinSet = viewModel.isPinSet()

    LaunchedEffect(state.toastMessage) {
        state.toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetToast()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    BigTittleText(text = stringResource(if (isPinSet) R.string.change_pin else R.string.set_pin))
                },
                navigationIcon = {
                    BackButton { navController.navigateUp() }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            //Icon
            Icon(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopCenter)
                    .padding(top = paddingLarge),
                painter = painterResource(id = com.transsion.financialassistant.presentation.R.drawable.reset_pin),
                contentDescription = "reset pin",
                tint = FAColors.green
            )


            //inputs
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(paddingMedium)
                    .fillMaxWidth()
            ) {

                if (isPinSet) {
                    //old pin
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingMediumLarge)
                    ) {
                        NormalText(
                            modifier = Modifier.padding(start = paddingMediumLarge),
                            text = stringResource(R.string.old_pin)
                        )
                        PasswordTextFieldFa(
                            value = state.oldPin,
                            onValueChange = { viewModel.onOldPinChange(it) },
                            placeholder = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingMediumLarge),
                            visualTransformation = { text ->
                                val transformedText = AnnotatedString(text.text.map { '*' }
                                    .joinToString(""))
                                TransformedText(transformedText, OffsetMapping.Identity)
                            }
                        )
                    }
                }

                //New Pin
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingMediumLarge)
                ) {
                    NormalText(
                        modifier = Modifier.padding(start = paddingMediumLarge),
                        text = stringResource(R.string.new_pin)
                    )
                    PasswordTextFieldFa(
                        value = state.newPin,
                        onValueChange = { viewModel.onNewPinChange(it) },
                        placeholder = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingMediumLarge),
                        visualTransformation = { text ->
                            val transformedText = AnnotatedString(text.text.map { '*' }
                                .joinToString(""))
                            TransformedText(transformedText, OffsetMapping.Identity)
                        },

                        )
                }

                if (!isPinSet) {
                    //Confirm new Pin
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingMediumLarge)
                    ) {
                        var isError by remember { mutableStateOf(false) }
                        NormalText(
                            modifier = Modifier.padding(start = paddingMediumLarge),
                            text = stringResource(R.string.confirm_pin)
                        )
                        PasswordTextFieldFa(
                            value = state.confirmPin,
                            onValueChange = {
                                viewModel.onConfirmPinChange(it)
                                isError = state.newPin.length > 3 && state.newPin != it
                            },
                            placeholder = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(paddingMediumLarge),
                            visualTransformation = { text ->
                                val transformedText = AnnotatedString(text.text.map { '*' }
                                    .joinToString(""))
                                TransformedText(transformedText, OffsetMapping.Identity)
                            },
                            isShowError = isError
                        )
                    }
                }
            }

            //save Button
            FilledButtonFa(
                enabled = if (isPinSet) state.oldPin.isNotBlank() && state.newPin.isNotBlank() else state.newPin.isNotBlank() && state.confirmPin.isNotBlank() && state.newPin == state.confirmPin,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(paddingLarge),
                text = stringResource(R.string.save),
                onClick = {
                    viewModel.onSavePin(onSuccess = {
                        viewModel.viewModelScope.launch {
                            viewModel.showToast(message = "Pin Changed Successfully")
                            //delay(1000)
                            //navController.navigateUp()
                        }
                    }
                    )
                }
            )
        }
    }

}