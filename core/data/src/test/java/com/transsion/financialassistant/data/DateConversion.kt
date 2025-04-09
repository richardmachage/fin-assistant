package com.transsion.financialassistant.data

import com.transsion.financialassistant.data.utils.toDbDate
import com.transsion.financialassistant.data.utils.toMpesaDate
import org.junit.Assert.assertTrue
import org.junit.Test

class DateConversion {

    @Test
    fun `test successful date conversion to db date`() {
        val date = "1/3/25"
        val convertedDate = date.toDbDate()
        assertTrue(convertedDate == "2025/03/01")
    }

    @Test
    fun `test successful date conversion to mpesa format`() {
        val date = "2025/03/01"
        val convertedDate = date.toMpesaDate()
        assertTrue(convertedDate == "1/3/25")

    }
}