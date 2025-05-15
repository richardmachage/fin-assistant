package com.transsion.financialassistant.data.di

import com.transsion.financialassistant.data.repository.pin.PinRepo
import com.transsion.financialassistant.data.repository.pin.PinRepoImpl
import com.transsion.financialassistant.data.repository.security.SecurityRepo
import com.transsion.financialassistant.data.repository.security.SecurityRepoImpl
import com.transsion.financialassistant.data.repository.transaction.TransactionRepo
import com.transsion.financialassistant.data.repository.transaction.TransactionRepoImpl
import com.transsion.financialassistant.data.repository.transaction.bundles_purchase.BundlesPurchaseRepo
import com.transsion.financialassistant.data.repository.transaction.bundles_purchase.BundlesPurchaseRepoImpl
import com.transsion.financialassistant.data.repository.transaction.buy_airtime.BuyAirtimeRepo
import com.transsion.financialassistant.data.repository.transaction.buy_airtime.BuyAirtimeRepoImpl
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepo
import com.transsion.financialassistant.data.repository.transaction.buy_goods.BuyGoodsRepoImpl
import com.transsion.financialassistant.data.repository.transaction.deposit.DepositRepo
import com.transsion.financialassistant.data.repository.transaction.deposit.DepositRepoImpl
import com.transsion.financialassistant.data.repository.transaction.fuliza_pay.FulizaPayRepo
import com.transsion.financialassistant.data.repository.transaction.fuliza_pay.FulizaPayRepoImpl
import com.transsion.financialassistant.data.repository.transaction.move_from_pochi.MoveFromPochiRepo
import com.transsion.financialassistant.data.repository.transaction.move_from_pochi.MoveFromPochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.move_to_pochi.MoveToPochiRepo
import com.transsion.financialassistant.data.repository.transaction.move_to_pochi.MoveToPochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepo
import com.transsion.financialassistant.data.repository.transaction.paybill.PayBillRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.receive_money.ReceiveMoneyRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_mshwari.ReceiveMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.receive_mshwari.ReceiveMshwariRepoImpl
import com.transsion.financialassistant.data.repository.transaction.receive_pochi.ReceivePochiRepo
import com.transsion.financialassistant.data.repository.transaction.receive_pochi.ReceivePochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_from_pochi.SendFromPochiRepo
import com.transsion.financialassistant.data.repository.transaction.send_from_pochi.SendFromPochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.send_money.SendMoneyRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_mshwari.SendMshwariRepo
import com.transsion.financialassistant.data.repository.transaction.send_mshwari.SendMshwariRepoImpl
import com.transsion.financialassistant.data.repository.transaction.send_pochi.SendPochiRepo
import com.transsion.financialassistant.data.repository.transaction.send_pochi.SendPochiRepoImpl
import com.transsion.financialassistant.data.repository.transaction.unknown.UnknownRepo
import com.transsion.financialassistant.data.repository.transaction.unknown.UnknownRepoImpl
import com.transsion.financialassistant.data.repository.transaction.withdraw_money.WithdrawMoneyRepo
import com.transsion.financialassistant.data.repository.transaction.withdraw_money.WithdrawMoneyRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindTransactionRepo(
        transactionRepoImpl: TransactionRepoImpl
    ): TransactionRepo

    @Binds
    internal abstract fun bindSecurityRepo(
        securityRepoImpl: SecurityRepoImpl
    ): SecurityRepo

    @Binds
    internal abstract fun bindSendMoneyRepo(
        sendMoneyRepoImpl: SendMoneyRepoImpl
    ): SendMoneyRepo

    @Binds
    internal abstract fun bindReceiveMoneyRepo(
        receiveMoneyRepoImpl: ReceiveMoneyRepoImpl
    ): ReceiveMoneyRepo

    @Binds
    internal abstract fun bindPayBillRepo(
        payBillRepoImpl: PayBillRepoImpl
    ): PayBillRepo

    @Binds
    internal abstract fun bindBuyGoodsRepo(
        buyGoodsRepoImpl: BuyGoodsRepoImpl
    ): BuyGoodsRepo

    @Binds
    internal abstract fun bindPinRepo(
        pinRepoImpl: PinRepoImpl
    ): PinRepo

    @Binds
    internal abstract fun bindWithdrawMoneyRepo(
        withdrawMoneyRepoImpl: WithdrawMoneyRepoImpl
    ): WithdrawMoneyRepo

    @Binds
    internal abstract fun bindSendMshwari(
        sendMshwariRepoImpl: SendMshwariRepoImpl
    ): SendMshwariRepo

    @Binds
    internal abstract fun bindBuyAirtimeRepo(
        buyAirtimeRepoImpl: BuyAirtimeRepoImpl
    ): BuyAirtimeRepo

    @Binds
    internal abstract fun bindBundlesPurchaseRepo(
        bundlesPurchaseRepoImpl: BundlesPurchaseRepoImpl
    ): BundlesPurchaseRepo

    @Binds
    internal abstract fun bindReceiveMshwariRepo(
        receiveMshwariRepoImpl: ReceiveMshwariRepoImpl
    ): ReceiveMshwariRepo

    @Binds
    internal abstract fun bindDepositMoneyDao(
        depositRepo: DepositRepoImpl
    ): DepositRepo

    @Binds
    internal abstract fun bindReceivePochiRepo(
        receivePochiRepo: ReceivePochiRepoImpl
    ): ReceivePochiRepo

    @Binds
    internal abstract fun bindSendPochiRepo(
        sendPochiRepo: SendPochiRepoImpl
    ): SendPochiRepo

    @Binds
    internal abstract fun bindMoveToPochiRepo(
        moveToPochiRepo: MoveToPochiRepoImpl
    ): MoveToPochiRepo

    @Binds
    internal abstract fun bindMoveFromPochi(
        moveFromPochiRepo: MoveFromPochiRepoImpl
    ): MoveFromPochiRepo

    @Binds
    internal abstract fun bindsSendMoneyFromPochi(
        sendFromPochiRepo: SendFromPochiRepoImpl
    ): SendFromPochiRepo

    @Binds
    internal abstract fun bindsFulizaPayRepo(
        fulizaPayRepo: FulizaPayRepoImpl
    ): FulizaPayRepo

    @Binds
    internal abstract fun bindsUnknownRepo(
        unknownRepo: UnknownRepoImpl
    ): UnknownRepo

}