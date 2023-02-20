package com.emq.extensions

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun DialogFragment.delayDismiss(duration: Long = 0) {
    lifecycleScope.launch {
        delay(duration)
        dismissAllowingStateLoss()
    }
}

fun DialogFragment.delayRun(duration: Long = 0, block: (DialogFragment) -> Unit = {}) {
    lifecycleScope.launch {
        delay(duration)
        block(this@delayRun)
    }
}