package com.common.extensions

import junit.framework.TestCase

class StringExtKtTest : TestCase() {

    fun testParseInternationalPhoneNumberWithZero() {
        val phoneNumber = "+8860919123456".parsePhoneNumber("TW")
        assertEquals(phoneNumber?.isValid(), true)
        assertEquals(phoneNumber?.countryCode, 886)
        assertEquals(phoneNumber?.nationalNumber, 919123456L)
        assertEquals(phoneNumber?.clearNationalNumber()?.nationalNumber, 0L)
    }

    fun testToE164PhoneNumber() {
        val phoneNumber = "0919000001".toE164PhoneNumber("TW")
        val e164PhoneNumber = "+886919000001".toE164PhoneNumber("TW")
        assertEquals(phoneNumber, "+886919000001")
        assertEquals(e164PhoneNumber, "+886919000001")
    }

    fun testToNationPhoneNumber() {
        val phoneNumber = "0919000001".toNationalPhoneNumber("TW")
        val e164PhoneNumber = "+886919000001".toNationalPhoneNumber("TW")
        assertEquals(phoneNumber, "0919000001")
        assertEquals(e164PhoneNumber, "0919000001")
    }

    fun testParseInternationalPhoneNumber() {
        val phoneNumber = "+886919123456".parsePhoneNumber("TW")
        assertEquals(phoneNumber?.countryCode, 886)
        assertEquals(phoneNumber?.nationalNumber, 919123456L)
        assertEquals(phoneNumber?.isValid(), true)
    }

    fun testParsePhoneNumber() {
        val phoneNumber = "0919123456".parsePhoneNumber("TW")
        assertEquals(phoneNumber?.countryCode, 886)
        assertEquals(phoneNumber?.nationalNumber, 919123456L)
        assertEquals(phoneNumber?.isValid(), true)
    }

    fun testParseInternationalPhoneNumberWithWrongRegionCode() {
        val phoneNumber = "+886919123456".parsePhoneNumber("US")
        assertTrue(phoneNumber?.countryCode == 886)
        assertEquals(phoneNumber?.nationalNumber, 919123456L)
        assertEquals(phoneNumber?.isValid(), true)
    }

    fun testParseWrongRegionCode() {
        val phoneNumber = "919123456".parsePhoneNumber("US")
        assertTrue(phoneNumber?.countryCode != 886)
        assertEquals(phoneNumber?.nationalNumber, 919123456L)
        assertEquals(phoneNumber?.isValid(), false)
    }

    fun testParseWrongPhoneNumber() {
        val phoneNumber = "19123456".parsePhoneNumber("TW")
        assertEquals(phoneNumber?.countryCode, 886)
        assertEquals(phoneNumber?.nationalNumber, 19123456L)
        assertEquals(phoneNumber?.isValid(), false)
    }

    fun testValidPhoneNumber() {
        val isValid = "919123456".parsePhoneNumber("TW")?.isValid()
        assertEquals(isValid, true)
    }

    fun testInvalidPhoneNumber() {
        val isValid = "191234".parsePhoneNumber("TW")?.isValid()
        assertEquals(isValid, false)
    }

    fun testEmail() {
        assertEquals("teddylin@emq.com".isValidEmail(), true)
        assertEquals("1@emq.com".isValidEmail(), true)
        assertEquals("_@emq.com".isValidEmail(), true)
    }

    fun testInvalidEmail() {
        assertEquals("@emq.com".isValidEmail(), false)
        assertEquals("teddy_lin".isValidEmail(), false)
        assertEquals("@".isValidEmail(), false)
        assertEquals("null".isValidEmail(), false)
        assertEquals(" ".isValidEmail(), false)
        assertEquals("teddy_li@#$%^%&^%\$n@emq.com".isValidEmail(), false)
    }

    fun testQRCodeContentToData() {
        val recipientID = "BW-011-2226543210987658"
        val dn = "2226543210987658"
        val data =
            "TWCBC://{\"recipientID\":\"$recipientID\",\"dn\":\"$dn\",\"won\":\"*\"}".qrCodeContentToData<QRCodeContent>()
        assertEquals(recipientID, data?.recipientID)
        assertEquals(dn, data?.dn)
    }

    private data class QRCodeContent(
        val recipientID: String,
        val dn: String
    )

    fun testCurrencyToLong() {
        assertEquals(1234, "$1234".currencyToLong())
        assertEquals(12341234, "$1234,1234".currencyToLong())
        assertEquals(12341234, "1234,1234".currencyToLong())
        assertEquals(0, "$0".currencyToLong())
        assertEquals(1, "$$1".currencyToLong())
        assertEquals(2, "$$$2".currencyToLong())
        assertEquals(0, "!@#$$$$2".currencyToLong())
    }

    fun testIsLegalMoneyFormat() {
        assertEquals(true, "$1234,1234".isLegalMoneyFormat())
        assertEquals(false, "$1234,!@#$1234".isLegalMoneyFormat())
        assertEquals(false, "$0".isLegalMoneyFormat())
        assertEquals(true, "$1".isLegalMoneyFormat())
    }
}