package com.transsion.financialassistant.home.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.transsion.financialassistant.presentation.components.texts.BigTittleText

@Composable
fun HomeScreen(){
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ){
            Column (
                modifier = Modifier
                    .fillMaxHeight(0.7f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                BigTittleText(
                    text = "Welcome to Financial Assistant"
                )
            }
        }
    }
}