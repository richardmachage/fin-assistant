package com.transsion.financialassistant.data.room.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        //Create FulizaPay table
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS FulizaPayEntity(
                    transactionCode TEXT NOT NULL PRIMARY KEY,
                    amount REAL NOT NULL,
                    availableFulizaLimit REAL NOT NULL,
                    phone TEXT NOT NULL,
                    mpesaBalance REAL NOT NULL,
                    transactionCategory TEXT NOT NULL,
                    date TEXT NOT NULL,
                    time TEXT NOT NULL,
                    transactionType TEXT NOT NULL
                ) 
            """.trimIndent()
        )


        // add the fuliza to outgoing transactions view

        //first drop old view
        //then create new view
        // Recreate the view with the FulizaPayEntity added
        db.execSQL("DROP VIEW IF EXISTS `UnifiedOutGoingTransaction`")

        db.execSQL(
            """
                CREATE VIEW `UnifiedOutGoingTransaction` AS SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost,mpesaBalance, NULL AS name FROM BundlesPurchaseEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory,transactionCost, mpesaBalance,NULL AS name FROM BuyAirtimeEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost,mpesaBalance, paidTo as name FROM BuyGoodsEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, mpesaBalance,paidToName as name FROM PayBillEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, mpesaBalance,sentToName as name FROM SendMoneyEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost,mpesaBalance, sentToName as name FROM SendPochiEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, transactionCost, mpesaBalance,NULL as name FROM SendMshwariEntity
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, "OUT" AS transactionCategory, transactionCost, mpesaBalance, "MY POCHI" as name FROM MoveToPochiEntity 
                UNION ALL
                SELECT transactionCode, phone, amount, date, time, transactionType, transactionCategory, 0.0 AS transactionCost, mpesaBalance,"Paid Fuliza" as name FROM FulizaPayEntity
        """
        )
    }

}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE IF NOT EXISTS UnknownEntity(
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    message TEXT NOT NULL,
                    timeStamp INTEGER NOT NULL
                ) 
            """.trimIndent()
        )
    }

}
