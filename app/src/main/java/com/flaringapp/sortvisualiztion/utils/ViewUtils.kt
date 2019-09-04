package com.flaringapp.sortvisualiztion.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import android.view.View.*
import android.view.ViewPropertyAnimator
import com.flaringapp.sortvisualiztion.constants.Constants.ANIM_DURATION
import com.flaringapp.sortvisualiztion.utils.ViewUtils.animateAppear
import com.flaringapp.sortvisualiztion.utils.ViewUtils.animateHide


object ViewUtils {

    fun animateAppear(view: View) {
        if (view.visibility == INVISIBLE or GONE) {
            view.visibility = VISIBLE
            view.alpha = 0f
        }

        animateAlpha(view, 1f).start()
    }

    fun animateHide(view: View) {
        animateAlpha(view, 0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = GONE
                }
            })
    }

    fun animateAlpha(view: View, to: Float): ViewPropertyAnimator {
        view.animate().cancel()
        return view.animate()
            .alpha(to)
            .setDuration(ANIM_DURATION)
    }
}

fun View.gone(animate: Boolean = true) {
    if (animate) animateHide(this)
    else visibility = GONE
}

fun View.show(animate: Boolean = true) {
    if (animate) animateAppear(this)
    else visibility = VISIBLE.also { alpha = 1f }
}