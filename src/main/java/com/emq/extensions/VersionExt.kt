package com.emq.extensions

import android.os.Build

fun equalOrHigherApi23() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M