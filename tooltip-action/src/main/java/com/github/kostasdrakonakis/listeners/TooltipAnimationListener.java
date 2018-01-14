package com.github.kostasdrakonakis.listeners;

import android.animation.Animator;
import android.view.View;

public interface TooltipAnimationListener {
    void onAnimationEntered(View view, Animator.AnimatorListener animatorListener);

    void onAnimationExited(View view, Animator.AnimatorListener animatorListener);
}
