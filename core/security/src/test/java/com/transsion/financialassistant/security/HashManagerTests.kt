package com.transsion.financialassistant.security

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class HashManagerTests {
    @Test
    fun `test salt generated is 32 bytes`() {
        val salt1 = HashingManager.generateSalt()
        val salt2 = HashingManager.generateSalt()

        assertEquals(32, salt1.size)
        assertEquals(32, salt2.size)
    }

    @Test
    fun `test salt generated is random`() {
        val salt1 = HashingManager.generateSalt()
        val salt2 = HashingManager.generateSalt()

        assertFalse(salt1.contentEquals(salt2))
    }

    @Test
    fun `test hashing consistency with same salt and input`() {
        val salt = HashingManager.generateSalt()
        val input = "test"

        val hash1 = HashingManager.hashData(input, salt)
        val hash2 = HashingManager.hashData(input, salt)

        assertTrue(hash1.contentEquals(hash2))
    }

    @Test
    fun `test different input generates different hash`() {
        val salt = HashingManager.generateSalt()
        val input1 = "test"
        val input2 = "another test"

        val hash1 = HashingManager.hashData(input1, salt)
        val hash2 = HashingManager.hashData(input2, salt)

        assertFalse(hash1.contentEquals(hash2))
    }

}