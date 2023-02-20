package com.common.extensions

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2


fun ViewPager2.reduceSensitivity() {
    try {
        val recyclerViewF = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewF.isAccessible = true
        val rv = recyclerViewF.get(this) as RecyclerView
        val touchSlopF = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopF.isAccessible = true
        val touchSlop = touchSlopF.get(rv) as Int
        touchSlopF.set(rv, touchSlop * 2)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

//Direction of vertical
fun ViewPager2.setCurrentItemWithDuration(
    item: Int,
    duration: Long,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    pagePxHeight: Int = height
) {
    val pxToDrag: Int = pagePxHeight * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator?) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(animation: Animator?) {
            endFakeDrag()
        }

        override fun onAnimationCancel(animation: Animator?) { /* Ignored */
        }

        override fun onAnimationRepeat(animation: Animator?) { /* Ignored */
        }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}