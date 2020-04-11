package com.github.kostasdrakonakis.listeners

import android.animation.Animator
import android.view.View

interface TooltipAnimationListener {
    fun onAnimationEntered(view: View, animatorListener: Animator.AnimatorListener)
    fun onAnimationExited(view: View, animatorListener: Animator.AnimatorListener)
}