package com.transsion.financialassistant.home.screens

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import com.transsion.financialassistant.home.viewModel.TransactionsViewModel
import com.transsion.financialassistant.onboarding.R
import com.transsion.financialassistant.presentation.components.dialogs.ConfirmDialog
import com.transsion.financialassistant.presentation.components.dialogs.InfoDialog
import com.transsion.financialassistant.presentation.components.isPermissionGranted
import com.transsion.financialassistant.presentation.components.requestMultiplePermissions
import com.transsion.financialassistant.presentation.components.texts.BigTittleText
import com.transsion.financialassistant.presentation.components.texts.NormalText
import com.transsion.financialassistant.presentation.utils.HorizontalSpacer
import com.transsion.financialassistant.presentation.utils.VerticalSpacer
import com.transsion.financialassistant.presentation.utils.paddingMedium
import com.transsion.financialassistant.presentation.utils.paddingSmall

@Composable
fun HomeScreen(
    receiveViewModel: TransactionsViewModel = hiltViewModel()
) {
    val receivedMoneyTransactions = receiveViewModel.reveivedMoneyTransactions.collectAsState()
    val context = LocalContext.current

    var isGranted by remember {
        mutableStateOf( context.isPermissionGranted(Manifest.permission.READ_SMS)
                //&& context.isPermissionGranted(Manifest.permission.READ_PHONE_NUMBERS)
        )
    }

    var showPermissionDialog by remember { mutableStateOf(false) }
    var showPermissionRationaleDialog by remember { mutableStateOf(false) }

    val permission = arrayOf(
        Manifest.permission.READ_SMS,
        //Manifest.permission.READ_PHONE_NUMBERS
    )

    val launcher = requestMultiplePermissions(
        onPermissionGranted = {
            isGranted = true
            showPermissionDialog = false
            showPermissionRationaleDialog = false
        },
        onPermissionDenied = {
            showPermissionRationaleDialog = true
        }
    )

    LaunchedEffect(isGranted) {
        if (isGranted) {
            receiveViewModel.fetchTransactions(context)
        }
    }

    Scaffold { paddingValues ->

        // Read SMS Permission Dialog
        ConfirmDialog(
            showDialog = showPermissionDialog,
            title = stringResource(R.string.sms_permission_title),
            message = stringResource(R.string.sms_permission_message),
            onDismiss = {
                showPermissionDialog = false
                showPermissionRationaleDialog = true
            },
            onConfirm = {
                showPermissionDialog = false
                launcher.launch(permission)
            },
            confirmButtonText = stringResource(R.string.allow),
            dismissButtonText = stringResource(R.string.deny)
        )

        // Read sms permission rationale dialog
        InfoDialog(
            showDialog = showPermissionRationaleDialog,
            title = stringResource(R.string.sms_permission_title),
            message = stringResource(R.string.sms_permission_rationale),
            onDismiss = {
                showPermissionRationaleDialog = false
                launcher.launch(permission)
            },
            buttonText = stringResource(R.string.allow)
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (isGranted) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalSpacer(32)

                    BigTittleText(
                        text = "Recent Receive Money Transactions - Test"
                    )

                    VerticalSpacer(16)

                    if (receivedMoneyTransactions.value.isEmpty()){
                        EmptyStateMessage()
                    } else {
                        TransactionList(receivedMoneyTransactions.value)
                    }
                }
            } else {
                showPermissionDialog = true
            }
        }
    }
}

@Composable
fun TransactionList(transactions: List<SendMoneyEntity>){
    LazyColumn {
        items(transactions){transaction ->
            TransactionItem(transaction)
        }
    }
}

@Composable
fun TransactionItem(transaction: SendMoneyEntity){
    Row  (
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ){
        // Avatar
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color.Gray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ){
            HorizontalSpacer(4)
            NormalText(
                text = transaction.sentToName.first().toString(),
                fontWeight = FontWeight.Bold
            )
        }
        VerticalSpacer(12)

        // Transaction Details
        Column(modifier = Modifier.weight(1f)) {
            NormalText(
                text = transaction.sentToName,
                fontWeight = FontWeight.Bold
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                TransactionTag(stringResource(R.string.received_money))  //TODO Have Custom Transaction Tag tag e.g General, Bills, etc
                HorizontalSpacer(8)
                TransactionTag(transaction.transactionCategory.toString()) // Custom category
            }
        }

        // Amount & Date
        Column(horizontalAlignment = Alignment.End) {
            NormalText(
                text = "+ KES ${transaction.amount}",
                textColor = Color.Red,
                fontWeight = FontWeight.Bold
            )
            BigTittleText(
                text = transaction.mpesaBalance.toString(),
                textColor = Color.Blue,
            )
            NormalText(
                text = transaction.date,
                fontSize = 12.sp,
                textColor = Color.Gray
            )
        }
    }
}

// Empty State Message - Incase no messages found
@Composable
fun EmptyStateMessage(){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        NormalText(
            text = stringResource(R.string.no_transactions_found)
        )
    }
}

// Custom Tag Composable
@Composable
fun TransactionTag(text: String){
    Box(
        modifier = Modifier
            .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = paddingMedium, vertical = paddingSmall)
    ){
        NormalText(
            text = text,
        )
    }
}