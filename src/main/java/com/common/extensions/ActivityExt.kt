package com.common.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)
        inputMethodManager?.hideSoftInputFromWindow(it.windowToken, 0)
    }
}



@ExperimentalCoroutinesApi
fun OnBackPressedDispatcher.back(owner: LifecycleOwner, action: () -> Unit) = callbackFlow {
    addCallback(owner) {
        action()
        trySend(Unit)
    }
    awaitClose {}
}