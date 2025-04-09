package com.transsion.financialassistant.data.repository.transaction

import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseEntity
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeEntity
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsEntity
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyEntity
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillEntity
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyEntity
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwariEntity
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiEntity
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariEntity
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiEntity
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyEntity

interface TransactionRepo {
    fun getTransactionType(message: String): TransactionType
    fun parseSendMoneyMessage(message: String, phone: String): SendMoneyEntity?
    fun parseReceiveMoneyMessage(message: String, phone: String): ReceiveMoneyEntity?
    fun parsePayBillMessage(message: String, phone: String): PayBillEntity?
    fun parseBuyGoodsMessage(message: String, phone: String): BuyGoodsEntity?
    fun parseSendPochiMessage(message: String, phone: String): SendPochiEntity?
    fun parseDepositMoneyMessage(message: String, phone: String): DepositMoneyEntity?
    fun parseReceivePochiMessage(message: String, phone: String): ReceivePochiEntity?
    fun parseBundlesPurchaseMessage(message: String, phone: String): BundlesPurchaseEntity?
    fun parseReceiveMshwariMessage(message: String, phone: String): ReceiveMshwariEntity?
    fun parseSendMshwariMessage(message: String, phone: String): SendMshwariEntity?
    fun parsePurchaseAirtimeMessage(message: String, phone: String): BuyAirtimeEntity?
    fun parseWithdrawMoneyMessage(message: String, phone: String): WithdrawMoneyEntity?
}
