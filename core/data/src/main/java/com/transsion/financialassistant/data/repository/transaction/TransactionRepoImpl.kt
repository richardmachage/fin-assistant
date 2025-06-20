package com.transsion.financialassistant.data.repository.transaction

import android.content.Context
import com.transsion.financialassistant.data.models.TransactionType
import com.transsion.financialassistant.data.repository.getDateMessageReceived
import com.transsion.financialassistant.data.room.entities.bundles_purchase.BundlesPurchaseEntity
import com.transsion.financialassistant.data.room.entities.buy_airtime.BuyAirtimeEntity
import com.transsion.financialassistant.data.room.entities.buygoods_till.BuyGoodsEntity
import com.transsion.financialassistant.data.room.entities.deposit.DepositMoneyEntity
import com.transsion.financialassistant.data.room.entities.fuliza_pay.FulizaPayEntity
import com.transsion.financialassistant.data.room.entities.move_from_pochi.MoveFromPochiEntity
import com.transsion.financialassistant.data.room.entities.move_to_pochi.MoveToPochiEntity
import com.transsion.financialassistant.data.room.entities.paybill_till.PayBillEntity
import com.transsion.financialassistant.data.room.entities.receive_money.ReceiveMoneyEntity
import com.transsion.financialassistant.data.room.entities.receive_mshwari.ReceiveMshwariEntity
import com.transsion.financialassistant.data.room.entities.receive_pochi.ReceivePochiEntity
import com.transsion.financialassistant.data.room.entities.receive_till.ReceiveTillEntity
import com.transsion.financialassistant.data.room.entities.reversal_credit.ReversalCreditEntity
import com.transsion.financialassistant.data.room.entities.reversal_debit.ReversalDebitEntity
import com.transsion.financialassistant.data.room.entities.send_from_pochi.SendFromPochiEntity
import com.transsion.financialassistant.data.room.entities.send_money.SendMoneyEntity
import com.transsion.financialassistant.data.room.entities.send_mshwari.SendMshwariEntity
import com.transsion.financialassistant.data.room.entities.send_pochi.SendPochiEntity
import com.transsion.financialassistant.data.room.entities.send_till.SendTillEntity
import com.transsion.financialassistant.data.room.entities.withdraw.WithdrawMoneyEntity
import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toDbDate
import com.transsion.financialassistant.data.utils.toDbTime
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

open class TransactionRepoImpl @Inject constructor(
) : TransactionRepo {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    override fun getTransactionType(message: String): TransactionType =
        TransactionType.entries.firstOrNull {
            it.getRegex().matches(message)
        } ?: TransactionType.UNKNOWN

    override fun parseSendMoneyMessage(message: String, phone: String): SendMoneyEntity? {

        val match = TransactionType.SEND_MONEY.getRegex().find(message) ?: return null
        //val groups = match.groupValues
        val group = match.groups

        return SendMoneyEntity(
            transactionCode = group["txnId1"]?.value ?: group["txnId2"]?.value ?: "",//groups[1],
            phone = phone,
            sentToName = group["recipient1"]?.value ?: group["recipient2"]?.value ?: "",//groups[3],
            sentToPhone = group["phone1"]?.value ?: "",
            amount = group["amount1"]?.value?.replace(",", "")?.toDouble()
                ?: group["amount2"]?.value?.replace(",", "")?.toDouble() ?: 0.0,
            mpesaBalance = group["balance1"]?.value?.replace(",", "")?.toDouble()
                ?: group["balance2"]?.value?.replace(",", "")?.toDouble() ?: 0.0,
            transactionCost = group["cost1"]?.value?.replace(",", "")?.toDoubleOrNull()
                ?: group["cost1"]?.value?.replace(",", "")?.toDoubleOrNull() ?: 0.0,
            date = group["date1"]?.value?.toDbDate() ?: group["date2"]?.value?.toDbDate()!!,
            time = group["time1"]?.value?.toDbTime() ?: group["time2"]?.value?.toDbTime()!!,
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
            time = groups[6].toDbTime(),
            date = groups[5].toDbDate()
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
            time = groups[6].toDbTime(),
            date = groups[5].toDbDate(),
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
            time = groups[5].toDbTime(),
            date = groups[4].toDbDate(),
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
            time = groups[5].toDbTime(),
            date = groups[4].toDbDate(),
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
            date = groups[2].toDbDate(),
            time = groups[3].toDbTime()
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
            date = groups[4].toDbDate(),
            time = groups[5].toDbTime(),
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
            date = groups[5].toDbDate(),
            time = groups[6].toDbTime(),
            transactionCost = groups[8].toDouble()
        )
    }

    override fun parseReceiveMshwariMessage(message: String, phone: String): ReceiveMshwariEntity? {
        val match = TransactionType.RECEIVE_MSHWARI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return ReceiveMshwariEntity(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[2].replace(",","").toDouble(),
            account = groups[3],
            date = groups[3].toDbDate(),
            time = groups[4].toDbTime(),
            mshwariBalance = groups[5].replace(",", "").toDouble(),
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            transactionCost = groups[7].toDouble()
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
            date = groups[3].toDbDate(),
            time = groups[4].toDbTime(),
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
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            date = groups[4].toDbDate(),
            time = groups[5].toDbTime(),
            transactionCost = groups[7].toDouble()
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
            date = groups[2].toDbDate(),
            time = groups[3].toDbTime(),
            agent = groups[5],
            transactionCost = groups[7].toDouble()
        )
    }

    override fun parseMoveToPochiMessage(message: String, phone: String): MoveToPochiEntity? {
        val match = TransactionType.MOVE_TO_POCHI.getRegex().find(message) ?: return null
        val groups = match.groupValues


        //FIXME should be removed in production
        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return MoveToPochiEntity(
            transactionCode = groups[1],
            amount = groups[2].replace(",", "").toDouble(),
            phone = phone,
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            date = groups[3].toDbDate(),
            time = groups[4].toDbTime(),
            businessBalance = groups[5].replace(",", "").toDouble(),
            transactionCost = groups[7].replace(",", "").toDouble()
        )
    }

    override fun parseMoveFromPochiMessage(message: String, phone: String): MoveFromPochiEntity? {
        val match = TransactionType.MOVE_FROM_POCHI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        //FIXME should be removed in production
        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return MoveFromPochiEntity(
            transactionCode = groups[1],
            amount = groups[2].replace(",", "").toDouble(),
            phone = phone,
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            date = groups[3].toDbDate(),
            time = groups[4].toDbTime(),
            businessBalance = groups[5].replace(",", "").toDouble(),
            transactionCost = groups[7].replace(",", "").toDouble()
        )

    }

    override fun parseSendFromPochiMessage(message: String, phone: String): SendFromPochiEntity? {
        val match = TransactionType.SEND_MONEY_FROM_POCHI.getRegex().find(message) ?: return null
        val groups = match.groupValues

        //FIXME should be removed in production
        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return SendFromPochiEntity(
            transactionCode = groups[1],
            amount = groups[2].replace(",", "").toDouble(),
            sentToName = groups[3],
            date = groups[4].toDbDate(),
            time = groups[5].toDbTime(),
            businessBalance = groups[6].replace(",", "").toDouble(),
            transactionCost = groups[7].replace(",", "").toDouble(),
            phone = phone,

            )

    }

    override fun parseReversalCreditMessage(message: String, phone: String): ReversalCreditEntity? {
        val match = TransactionType.REVERSAL_CREDIT.getRegex().find(message) ?: return null
        val groups = match.groupValues

        //FIXME should be removed in production
        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        return ReversalCreditEntity(
            transactionCode = groups[1],
            transactionReversedCode = groups[2],
            date = groups[3].toDbDate(),
            time = groups[4].toDbTime(),
            amount = groups[5].replace(",", "").toDouble(),
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            phone = phone,
        )
    }

    override fun parseReversalDebitMessage(message: String, phone: String): ReversalDebitEntity? {
        val match = TransactionType.REVERSAL_DEBIT.getRegex().find(message) ?: return null
        val groups = match.groupValues

        return ReversalDebitEntity(
            transactionCode = groups[1],
            transactionReversedCode = groups[2],
            date = groups[3].toDbDate(),
            time = groups[4].toDbTime(),
            amount = groups[5].replace(",", "").toDouble(),
            mpesaBalance = groups[6].replace(",", "").toDouble(),
            phone = phone,
        )
    }

    override fun parseReceiveTillMessage(message: String, phone: String): ReceiveTillEntity? {
        val match = TransactionType.RECEIVE_TILL.getRegex().find(message) ?: return null
        val groups = match.groupValues

        return ReceiveTillEntity(
            transactionCode = groups[1],
            phone = phone,
            amount = groups[4].replace(",", "").toDouble(),
            receiveFromName = groups[6],
            receiveFromNumber = groups[5],
            date = groups[2].toDbDate(),
            time = groups[3].toDbTime(),
            businessBalance = groups[7].replace(",", "").toDouble(),
            transactionCost = groups[8].replace(",", "").toDouble()
        )
    }

    override fun parseSendTillMessage(message: String, phone: String): SendTillEntity? {
        val match = TransactionType.SEND_FROM_TILL.getRegex().find(message) ?: return null
        val groups = match.groupValues

        return SendTillEntity(
            transactionCode = groups[1],
            amount = groups[2].replace(",", "").toDouble(),
            sentToName = groups[3],
            date = groups[4].toDbDate(),
            time = groups[5].toDbTime(),
            businessBalance = groups[6].replace(",", "").toDouble(),
            phone = phone
        )
    }


    override fun parseFulizaPayMessage(
        message: String,
        phone: String,
        isTest: Boolean
    ): FulizaPayEntity? {
        val match = TransactionType.FULIZA_PAY.getRegex().find(message) ?: return null
        val groups = match.groupValues

        groups.forEachIndexed { index, it ->
            println("${index}, $it")
        }

        val transactionCode = groups[1]
        val timeMills = if (isTest) System.currentTimeMillis() else {
            getDateMessageReceived(context, transactionCode = transactionCode).getOrNull()
        }

        return timeMills?.let {
            FulizaPayEntity(
                transactionCode = groups[1],
                phone = phone,
                amount = groups[2].replace(",", "").toDouble(),
                mpesaBalance = groups[4].replace(",", "").toDouble(),
                date = it.toDbDate(),
                time = it.toAppTime(),
                availableFulizaLimit = groups[3].replace(",", "").toDouble()

            )
        }

    }


}