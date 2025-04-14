package com.transsion.financialassistant.data

import com.transsion.financialassistant.data.utils.toAppTime
import com.transsion.financialassistant.data.utils.toDbDate
import com.transsion.financialassistant.data.utils.toDbTime
import com.transsion.financialassistant.data.utils.toMpesaDate
import org.junit.Assert.assertEquals
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

    @Test
    fun `toDbTime should convert 12-hour format to 24-hour format`() {
        val input = "11:30 PM"
        val expected = "23:30"
        val result = input.toDbTime()
        assertEquals(expected, result)
    }

    @Test
    fun `toAppTime should convert 24-hour format to 12-hour format`() {
        val input = "15:05"
        val expected = "3:05 PM"
        val result = input.toAppTime()
        assertEquals(expected, result)
    }

    @Test
    fun `toDbTime should return original string on parse failure`() {
        val input = "not a time"
        val result = input.toDbTime()
        assertEquals("not a time", result)
    }

    @Test
    fun `toAppTime should return original string on parse failure`() {
        val input = "99:99"
        val result = input.toAppTime()
        assertEquals("99:99", result)
    }

}