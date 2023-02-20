package com.emq.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.annotation.StringRes
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun Context.getSafeString(@StringRes id: Int?, vararg args: Any): String? =
    try {
        this.getString(id ?: 0, *args)
    } catch (e: Exception) {
        null
    }

suspend inline fun <reified T> Context.parseAssetsJsonArray(fileName: String): List<T>? {
    return withContext(Dispatchers.IO) {
        try {
            val moshi = Moshi.Builder().build()
            val inputStream = this@parseAssetsJsonArray.assets.open(fileName)
            val json = inputStream.bufferedReader().use { it.readText() }
            val listType = Types.newParameterizedType(List::class.java, T::class.java)
            val adapter: JsonAdapter<List<T>> = moshi.adapter(listType)
            adapter.fromJson(json)
        } catch (e: Exception) {
            null
        }
    }
}

fun Context.copyText(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData: ClipData = ClipData.newPlainText("SCSBWalletCopyText", text)
    clipboard.setPrimaryClip(clipData)
}