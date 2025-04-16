package com.transsion.financialassistant.background

import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.bundles_purchase.BundlesPurchaseRepo
import com.transsion.financialassistant.data.repository.transaction.buy_airtime.BuyAirtimeRepo
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepo
import com.transsion.financialassistant.data.repository.transaction.deposit.DepositRepo
import com.transsion.financialassistant.data.repository.transaction.move_to_pochi.MoveToPochiRepo
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepo
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.receive_mshwari.ReceiveMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.receive_pochi.ReceivePochiRepo
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.send_mshwari.SendMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.send_pochi.SendPochiRepo
import com.transsion.financialassistant.data.repository.transaction.withdraw_money.WithdrawMoneyRepo
import javax.inject.Inject

class Repos @Inject constructor(
    val withdrawMoneyRepo: WithdrawMoneyRepo,
    val sendMoneyRepo: SendMoneyRepo,
    val receiveMoneyRepo: ReceiveMoneyRepo,
    val sendMshwariRepo: SendMshwariRepo,
    val payBillRepo: PayBillRepo,
    val buyGoodsRepo: BuyGoodsRepo,
    val buyAirtime: BuyAirtimeRepo,
    val depositRepo: DepositRepo,
    val bundlesPurchaseRepo: BundlesPurchaseRepo,
    val receiveMshwariRepo: ReceiveMshwariRepo,
    val receivePochiRepo: ReceivePochiRepo,
    val sendPochiRepo: SendPochiRepo,
    val transactionRepo: TransactionRepo,
    val moveToPochiRepo: MoveToPochiRepo
)