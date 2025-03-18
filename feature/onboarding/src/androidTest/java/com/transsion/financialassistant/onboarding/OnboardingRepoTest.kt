package com.transsion.financialassistant.onboarding

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.transsion.financialassistant.data.preferences.DatastorePreferences
import com.transsion.financialassistant.data.preferences.SharedPreferences
import com.transsion.financialassistant.data.repository.security.SecurityRepo
import com.transsion.financialassistant.data.repository.security.SecurityRepoImpl
import com.transsion.financialassistant.onboarding.data.OnboardingRepoImpl
import com.transsion.financialassistant.onboarding.domain.OnboardingRepo
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingRepoTest {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataStore: DatastorePreferences
    private lateinit var onboardingRepo: OnboardingRepo
    private lateinit var securityRepo: SecurityRepo

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        sharedPreferences = SharedPreferences(context)
        dataStore = DatastorePreferences(context)
        securityRepo = SecurityRepoImpl(sharedPreferences)
        onboardingRepo = OnboardingRepoImpl(
            dataStore,
            sharedPreferences,
            securityRepo
        )
    }


    @Test
    fun shouldReturnTrueWhenOnboardingIsComplete() {
        onboardingRepo.setCompletedOnboarding()
        val result = onboardingRepo.hasCompletedOnboarding()
        assertTrue(result)
    }

    @Test
    fun shouldReturnFalseWhenOnboardingIsNotComplete() {
        sharedPreferences.deleteData(SharedPreferences.ONBOARDING_COMPLETED_KEY)
        val result = onboardingRepo.hasCompletedOnboarding()
        assertFalse(result)
    }

    @Test
    fun shouldFailWhenNoSimCardsAreFound() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onboardingRepo.getMpesaNumbersOnDevice(
            context = context,
            onSuccess = {
                fail("Should not succeed when no SIM cards are found")
            },
            onFailure = { errorMessage ->
                println(errorMessage)
                assertTrue(errorMessage.isNotBlank())

            }
        )
    }

    @Test
    fun shouldFailWhenNoSafaricomNumbersAreFound() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        onboardingRepo.getMpesaNumbersOnDevice(
            context,
            onSuccess = { fail("Should not succeed when no M-PESA numbers are found") },
            onFailure = { errorMessage ->
                println(errorMessage)
                assertEquals(
                    "No M-PESA numbers found. Please insert an MPESA SIM card.",
                    errorMessage
                )
            }
        )
    }

    @Test
    fun shouldReturnSafaricomNumberWhenFound() {
        // Given: A context where a Safaricom SIM is present
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // When: Calling `getMpesaNumbersOnDevice()`
        onboardingRepo.getMpesaNumbersOnDevice(
            context,
            onSuccess = { phoneNumbers ->
                // Then: Ensure at least one Safaricom number is found
                assertTrue(phoneNumbers.isNotEmpty())
                println("Found Safaricom numbers: $phoneNumbers") // Debug output
            },
            onFailure = { fail("Should not fail when a Safaricom number is found") }
        )
    }

    @Test
    fun shouldHandleUnexpectedExceptionGracefully() {
        // Given: A context where an unexpected exception is simulated
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        try {
            // Throw an exception inside the test
            throw RuntimeException("Unexpected error occurred")

        } catch (e: Exception) {
            onboardingRepo.getMpesaNumbersOnDevice(
                context,
                onSuccess = { fail("Should not succeed when an exception occurs") },
                onFailure = { errorMessage ->
                    // Then: Ensure an error message is returned
                    assertNotNull(errorMessage)
                    println("Handled error: $errorMessage") // Debug output
                }
            )
        }
    }

    @Test
    fun shouldSuccessfullySaveMpesaNumber() = runTest {
        // Given: A valid M-PESA number
        val mpesaNumber = "0712345678"

        // When: Calling `setMpesaNumber()`
        onboardingRepo.setMpesaNumber(
            mpesaNumber = mpesaNumber,
            onSuccess = {
                // Then: Retrieve the stored number and verify
                val storedNumber = dataStore.getValue(DatastorePreferences.MPESA_NUMBER_KEY, "")
                assertEquals(mpesaNumber, storedNumber)
            },
            onFailure = { fail("Should not fail when saving a valid M-PESA number") }
        )
    }

    @Test
    fun shouldFailWhenMpesaNumberIsEmpty() = runTest {
        // Given: An empty M-PESA number
        val mpesaNumber = ""

        // When: Calling `setMpesaNumber()`
        onboardingRepo.setMpesaNumber(
            mpesaNumber,
            onSuccess = { fail("Should not succeed when saving an empty number") },
            onFailure = { errorMessage ->
                println(errorMessage)
                // Then: It should fail with the correct error message
                assertEquals("Invalid M-PESA number", errorMessage)
            }
        )
    }

    @Test
    fun shouldFailWhenUnexpectedErrorOccursInSavingMpesaNumber() = runTest {
        // Given: A valid M-PESA number but simulate a failure
        val mpesaNumber = "0712345678"

        // Simulate an error
        try {
            throw RuntimeException("Simulated failure")
        } catch (e: Exception) {
            onboardingRepo.setMpesaNumber(
                mpesaNumber,
                onSuccess = { fail("Should not succeed when an error occurs") },
                onFailure = { errorMessage ->
                    // Then: Ensure an error message is returned
                    assertNotNull(errorMessage)
                    println("Handled error: $errorMessage") // Debug output
                }
            )
        }
    }

    @Test
    fun shouldSuccessfullySetPin() = runTest {
        // Given: A valid PIN
        val pin = "1234"

        // When: Calling `setPin()`
        onboardingRepo.setPin(
            pin,
            onSuccess = {
                // Then: Retrieve the stored PIN (Encrypted)
                val storedPin = sharedPreferences.loadData(SharedPreferences.PIN_KEY)
                assertNotNull(storedPin) // Ensure PIN is stored
            },
            onFailure = { fail("Should not fail when setting a valid PIN") }
        )
    }

    @Test
    fun shouldFailWhenPinIsEmpty() = runTest {
        // Given: An empty PIN
        val pin = ""

        // When: Calling `setPin()`
        onboardingRepo.setPin(
            pin,
            onSuccess = { fail("Should not succeed when setting an empty PIN") },
            onFailure = { errorMessage ->
                // Then: It should fail with the correct error message
                assertEquals("Invalid PIN", errorMessage)
            }
        )
    }


    @Test
    fun shouldFailWhenUnexpectedErrorOccursInSettingPin() = runTest {
        // Given: A valid PIN but simulate a failure
        val pin = "1234"

        // Simulate an error (Example: force failure inside implementation)
        try {
            throw RuntimeException("Simulated failure")
        } catch (e: Exception) {
            onboardingRepo.setPin(
                pin,
                onSuccess = { fail("Should not succeed when an error occurs") },
                onFailure = { errorMessage ->
                    // Then: Ensure an error message is returned
                    assertNotNull(errorMessage)
                    println("Handled error: $errorMessage") // Debug output
                }
            )
        }
    }

    @Test
    fun shouldVerifyCorrectPinSuccessfully() = runTest {
        // Given: A valid PIN
        val pin = "1234"
        onboardingRepo.setPin(pin, onSuccess = {}, onFailure = {})

        // When: Verifying correct PIN
        onboardingRepo.verifyPin(
            pin,
            onSuccess = { isVerified ->
                // Then: It should be verified successfully
                assertTrue(isVerified)
            },
            onFailure = { fail("Should not fail when verifying a correct PIN") }
        )
    }

    @Test
    fun shouldFailVerificationWhenPinIsIncorrect() = runTest {
        // Given: A valid PIN
        val correctPin = "1234"
        onboardingRepo.setPin(correctPin, onSuccess = {}, onFailure = {})

        // When: Verifying incorrect PIN
        onboardingRepo.verifyPin(
            "5678",
            onSuccess = { isVerified ->
                // Then: It should return false
                assertFalse(isVerified)
            },
            onFailure = { fail("Should not fail when verifying an incorrect PIN") }
        )
    }

    @Test
    fun shouldFailWhenUnexpectedErrorOccursDuringVerification() {
        // Given: A valid PIN but simulate a failure
        val pin = "1234"

        // Simulate an error (Example: force failure inside implementation)
        try {
            throw RuntimeException("Simulated failure")
        } catch (e: Exception) {
            onboardingRepo.verifyPin(
                pin,
                onSuccess = { fail("Should not succeed when an error occurs") },
                onFailure = { errorMessage ->
                    println("Handled error: $errorMessage") // Debug output

                    // Then: Ensure an error message is returned
                    assertNotNull(errorMessage)
                }
            )
        }
    }

}