package com.github.kostasdrakonakis.android.animations

import android.animation.Animator
import android.view.View
import com.github.kostasdrakonakis.listeners.TooltipAnimationListener

class FadeAnimation : TooltipAnimationListener {
    private var fadeDuration: Long = 400

    constructor(): this(400)
    constructor(fadeDuration: Long) {
        this.fadeDuration = fadeDuration
    }

    override fun onAnimationEntered(view: View, animatorListener: Animator.AnimatorListener) {
        view.alpha = 0f
        view.animate().alpha(1f).setDuration(fadeDuration).setListener(animatorListener)
    }

    override fun onAnimationExited(view: View, animatorListener: Animator.AnimatorListener) {
        view.animate().alpha(0f).setDuration(fadeDuration).setListener(animatorListener)
    }
}