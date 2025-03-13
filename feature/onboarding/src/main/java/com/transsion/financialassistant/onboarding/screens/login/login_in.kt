package com.transsion.financialassistant.onboarding.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.wear.compose.material.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Text
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.onboarding.screens.create_pin.CreatePinScreenViewModel
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.FaintText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.theme.FAColors
import com.transsion.financialassistant.presentation.utils.VerticalSpacer

@Composable
fun LoginScreen(
    viewModel: CreatePinScreenViewModel = hiltViewModel()
) {
    var pin by remember { mutableStateOf("") } // Holds the PIN Value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(top = 144.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            BigTittleText(
                text = stringResource(viewModel.getGreetingBasedOnTime())
            )
            VerticalSpacer(8)

            FaintText(text = stringResource(R.string.login_description))

            VerticalSpacer(16)

            // PIN Display
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .border(
                                BorderStroke(2.dp, FAColors.green),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index < pin.length) {
                          NormalText(
                              text =if (index < pin.length) "*" else "",
                          )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // KeyPad Number Layout
            val keyPadNumbers = listOf(
                listOf("1", "2", "3"),
                listOf("4", "5", "6"),
                listOf("7", "8", "9")
            )

            keyPadNumbers.forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    row.forEach { digit ->
                        NumberButton(digit) {
                            if (pin.length < 4) pin += digit
                        }
                    }
                }
                VerticalSpacer(8)
            }

            // Last row: Fingerprint, 0, Delete
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Fingerprint Button
                CircularIconButton(com.transsion.financialassistant.presentation.R.drawable.ic_outline_fingerprint) {
                    // Implement Fingerprint Authentication
                }

                // Zero Button
                NumberButton("0") {
                    if (pin.length < 4) pin += "0"
                }

                // Delete Button
                CircularIconButton(com.transsion.financialassistant.presentation.R.drawable.backspace) {
                    if (pin.isNotEmpty()) pin = pin.dropLast(1)
                }
            }
        }
    }
}

// Reusable Number Button
@Composable
fun NumberButton(number: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape),
        colors = ButtonDefaults.buttonColors(backgroundColor = FAColors.lightGreen)
    ) {
        Text(
            text = number,
            color = FAColors.green,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

// Reusable Circular Icon Button
@Composable
fun CircularIconButton(icon: Int, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(FAColors.lightGreen)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = FAColors.green,
            modifier = Modifier.size(32.dp)
        )
    }
}
