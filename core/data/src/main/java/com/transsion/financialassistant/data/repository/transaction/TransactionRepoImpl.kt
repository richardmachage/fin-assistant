package com.transsion.financialassistant.data.repository.transaction

import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseEntity
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeEntity
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsEntity
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyEntity
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillEntity
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyEntity
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwari
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiEntity
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariEntity
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiEntity
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyEntity
import javax.inject.Inject

open class TransactionRepoImpl @Inject constructor() : TransactionRepo {
    override fun getTransactionType(message: String): TransactionType =
        when {
            TransactionType.RECEIVE_MONEY.getRegex()
                .matches(message) -> TransactionType.RECEIVE_MONEY

            TransactionType.BUNDLES_PURCHASE.getRegex()
                .matches(message) -> TransactionType.BUNDLES_PURCHASE

            TransactionType.AIRTIME_PURCHASE.getRegex()
                .matches(message) -> TransactionType.AIRTIME_PURCHASE

            TransactionType.DEPOSIT.getRegex()
                .matches(message) -> TransactionType.DEPOSIT

            TransactionType.WITHDRAWAL.getRegex()
                .matches(message) -> TransactionType.WITHDRAWAL

            TransactionType.PAY_BILL.getRegex()
                .matches(message) -> TransactionType.PAY_BILL

            TransactionType.SEND_MONEY.getRegex()
                .matches(message) -> TransactionType.SEND_MONEY

            TransactionType.RECEIVE_MONEY.getRegex()
                .matches(message) -> TransactionType.RECEIVE_MONEY

            TransactionType.BUY_GOODS.getRegex()
                .matches(message) -> TransactionType.BUY_GOODS

            TransactionType.SEND_MSHWARI.getRegex()
                .matches(message) -> TransactionType.SEND_MSHWARI

            TransactionType.RECEIVE_POCHI.getRegex()
                .matches(message) -> TransactionType.RECEIVE_POCHI

            TransactionType.RECEIVE_MSHWARI.getRegex()
                .matches(message) -> TransactionType.RECEIVE_MSHWARI

            TransactionType.SEND_POCHI.getRegex()
                .matches(message) -> TransactionType.SEND_POCHI
            else -> TransactionType.UNKNOWN
        }

    override fun parseSendMoneyMessage(message: String, phone: String): SendMoneyEntity? {

        val match = TransactionType.SEND_MONEY.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index} : $it")
        }

        return SendMoneyEntity(
            transactionCode = groups[1],
            phone = phone,
            sentToName = groups[3],
            sentToPhone = groups[4],
            amount = groups[2].replace(",", "").toDouble(),
            mpesaBalance = groups[7].replace(",", "").toDouble(),
            transactionCost = groups[8].replace(",", "").toDouble(),
            date = groups[5],
            time = groups[6]
        )
    }

    override fun parseReceiveMoneyMessage(message: String, phone: String): ReceiveMoneyEntity? {

        val match = TransactionType.RECEIVE_MONEY.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}: $it")
        }
        return ReceiveMoneyEntity(
            transactionCode = groups[1],
            phone = phone,
            receiveFromName = groups[3],
            receiveFromPhone = groups[4],
            amount = groups[2].replace("," , "").toDouble(),
            mpesaBalance = groups[7].replace(",", "").toDouble(),
            time = groups[6],
            date = groups[5]
        )
    }

    override fun parsePayBillMessage(message: String, phone: String): PayBillEntity? {
        val match = TransactionType.PAY_BILL.getRegex().find(message) ?: return null

        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}: $it")
        }

        return PayBillEntity(
            transactionCode = groups[1],
            phone = phone,
            paidToName = groups[3],
            paidToAccountNo = groups[4],
            amount = groups[2].replace(",","").toDouble(),
            mpesaBalance = groups[7].replace(",","").toDouble(),
            time = groups[6],
            date = groups[5],
            transactionCost = groups[8].toDouble(),
        )
    }

    override fun parseBuyGoodsMessage(message: String, phone: String): BuyGoodsEntity? {
        val match = TransactionType.BUY_GOODS.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return BuyGoodsEntity(
            transactionCode = groups[1],
            phone = phone,
            paidTo = groups[3],
            amount = groups[2].replace(",", "").toDouble(),
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            time = groups[5],
            date = groups[4],
            transactionCost = groups[7].toDouble()

        )
    }

    override fun parseSendPochiMessage(message: String, phone: String): SendPochiEntity? {
        val match = TransactionType.SEND_POCHI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return SendPochiEntity (
            transactionCode = groups[1],
            phone = phone,
            sentToName = groups[3],
            amount = groups[2].replace(",", "").toDouble(),
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            time = groups[5],
            date = groups[4],
            transactionCost = groups[7].toDouble()
        )
    }

    override fun parseDepositMoneyMessage(message: String, phone: String): DepositMoneyEntity? {
       val match = TransactionType.DEPOSIT.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed {index, it ->
            println("${index}, $it")
        }

        return DepositMoneyEntity(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[4].replace(",","").toDouble(),
            agentDepositedTo = groups[5],
            mpesaBalance = groups[6].replace(",","").toDouble(),
            date = groups[2],
            time = groups[3]
        )
    }

    override fun parseReceivePochiMessage(message: String, phone: String): ReceivePochiEntity? {
        val match = TransactionType.RECEIVE_POCHI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return ReceivePochiEntity (
            transactionCode = groups[1],
            phone = phone,
            amount = groups[2].replace(",","").toDouble(),
            receiveFromName = groups[3],
            date = groups[4],
            time = groups[5],
            businessBalance = groups[6].replace(",","").toDouble()
        )
    }

    override fun parseBundlesPurchaseMessage(message: String, phone: String): BundlesPurchaseEntity? {
        val match = TransactionType.BUNDLES_PURCHASE.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return BundlesPurchaseEntity(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[2].replace(",","").toDouble(),
            accountName = groups[3],
            accountNumber = groups[4],
            mpesaBalance = groups[7].replace(",","").toDouble(),
            date = groups[5],
            time = groups[6],
            transactionCost = groups[8].toDouble()
        )
    }

    override fun parseReceiveMshwariMessage(message: String, phone: String): ReceiveMshwari? {
        val match = TransactionType.RECEIVE_MSHWARI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return ReceiveMshwari(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[2].replace(",","").toDouble(),
            account = groups[3],
            date = groups[4],
            time = groups[5],
            mshwariBalance = groups[6].replace(",","").toDouble(),
            mpesaBalance =  groups[7].replace(",", "").toDouble(),
            transactionCost = groups[8].toDouble()
        )
    }

    override fun parseSendMshwariMessage(message: String, phone: String): SendMshwariEntity? {
        val match = TransactionType.SEND_MSHWARI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return SendMshwariEntity(
            transactionCode = groups[1],
            phone = phone,
           //account = groups[3],
            amount = groups[2].replace(",","").toDouble(),
            mpesaBalance = groups[5].replace(",","").toDouble(),
            date = groups[3],
            time = groups[4],
            mshwariBalance = groups[6].replace(",","").toDouble(),
            transactionCost = groups[7].toDouble()
        )
    }

    override fun parsePurchaseAirtimeMessage(message: String, phone: String): BuyAirtimeEntity? {
        val match = TransactionType.AIRTIME_PURCHASE.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return BuyAirtimeEntity(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[2].replace(",","").toDouble(),
            mpesaBalance = groups[5].replace(",","").toDouble(),
            date = groups[3],
            time = groups[4],
            transactionCost = groups[6].toDouble()
        )
    }

    override fun parseWithdrawMoneyMessage(message: String, phone: String): WithdrawMoneyEntity? {
        val match = TransactionType.WITHDRAWAL.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return WithdrawMoneyEntity(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[4].replace(",","").toDouble(),
            mpesaBalance = groups[6].replace(",","").toDouble(),
            date = groups[2],
            time = groups[3],
            //agentName = groups[5],
            agent = groups[5],
            transactionCost = groups[7].toDouble()
        )
    }

}