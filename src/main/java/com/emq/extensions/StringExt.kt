package com.emq.extensions

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.util.PatternsCompat
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.common.BitMatrix
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.QRCodeWriter
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import com.squareup.moshi.Moshi
import java.util.*

fun Phonenumber.PhoneNumber.isValid() =
    try {
        PhoneNumberUtil.getInstance().isValidNumber(this)
    } catch (e: Exception) {
        false
    }

fun String.parsePhoneNumber(regionCode: String): Phonenumber.PhoneNumber? =
    try {
        PhoneNumberUtil.getInstance().parse(this, regionCode)
    } catch (e: Exception) {
        null
    }

fun String.toE164PhoneNumber(regionCode: String): String? =
    try {
        val phoneNumber = PhoneNumberUtil.getInstance().parse(this, regionCode)
        "+${phoneNumber.countryCode}${phoneNumber.nationalNumber}"
    } catch (e: Exception) {
        null
    }

fun String.toNationalPhoneNumber(regionCode: String): String? =
    try {
        val phoneNumber = PhoneNumberUtil.getInstance().parse(this, regionCode)
        val numberOfZero = phoneNumber.numberOfLeadingZeros
        String.format("%0${numberOfZero}d${phoneNumber.nationalNumber}", 0)
    } catch (e: Exception) {
        null
    }

fun String.isValidEmail() = PatternsCompat.EMAIL_ADDRESS.toRegex().matches(this)

fun String.formatMoney(): String {
    return try {
        this.replace(",", "").replace(".", "").toLong().formatMoney()
    } catch (exception: NumberFormatException) {
        exception.printStackTrace()
        "0.00"
    }
}

fun String.currencyToLong() = try {
    replace("$", "")
        .replace(".", "")
        .replace(",", "")
        .toLong()
} catch (e: Exception) {
    e.printStackTrace()
    0
}

fun String.isLegalMoneyFormat(): Boolean = currencyToLong() > 0

fun String.qrCode(
    width: Int = 512,
    height: Int = 512
): Bitmap {
    val hints = createEncodeHints()
    val bits = QRCodeWriter().encode(this, BarcodeFormat.QR_CODE, width, height, hints)
    return bits.createBitmap(width, height)
}

fun String.barCode(
    width: Int = 512,
    height: Int = 80
): Bitmap {
    val hints = createEncodeHints()
    val bits = Code128Writer().encode(this, BarcodeFormat.CODE_128, width, height, hints)
    return bits.createBitmap(width, height)
}

private fun createEncodeHints() =
    EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        .apply {
            this[EncodeHintType.CHARACTER_SET] = "UTF-8"
            this[EncodeHintType.MARGIN] = 0
            this[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        }

private fun BitMatrix.createBitmap(width: Int, height: Int) =
    Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565).also {
        for (y in 0 until height) {
            for (x in 0 until width) {
                it.setPixel(x, y, if (this[x, y]) Color.BLACK else Color.WHITE)
            }
        }
    }

inline fun <reified T> String.qrCodeContentToData(): T? {
    return try {
        val moshi = Moshi.Builder().build()
        val result = "\\{.*\\}".toRegex().find(this)
        val adapter = moshi.adapter(T::class.java)
        result?.let { adapter.fromJson(it.value) }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.asExample() = "i.e., $this"