package com.github.kostasdrakonakis.android.animations;

import android.animation.Animator;
import android.view.View;

import com.github.kostasdrakonakis.listeners.TooltipAnimationListener;

public class FadeAnimation implements TooltipAnimationListener {

    private long fadeDuration = 400;

    public FadeAnimation() {
    }

    public FadeAnimation(long fadeDuration) {
        this.fadeDuration = fadeDuration;
    }

    @Override
    public void onAnimationEntered(View view, Animator.AnimatorListener animatorListener) {
        view.setAlpha(0);
        view.animate().alpha(1).setDuration(fadeDuration).setListener(animatorListener);
    }

    @Override
    public void onAnimationExited(View view, Animator.AnimatorListener animatorListener) {
        view.animate().alpha(0).setDuration(fadeDuration).setListener(animatorListener);
    }
}
