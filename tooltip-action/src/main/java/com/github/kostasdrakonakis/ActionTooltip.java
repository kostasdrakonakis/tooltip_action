package com.github.kostasdrakonakis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Px;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.github.kostasdrakonakis.android.animations.FadeAnimation;
import com.github.kostasdrakonakis.android.views.TooltipActionView;
import com.github.kostasdrakonakis.enums.TooltipAlign;
import com.github.kostasdrakonakis.enums.TooltipPosition;
import com.github.kostasdrakonakis.listeners.DisplayListener;

public class ActionTooltip {

    private final View view;
    private final TooltipActionView tooltipView;

    private ActionTooltip(Context context, View view) {
        this.view = view;
        this.tooltipView = new TooltipActionView(context);
        final NestedScrollView scrollParent = findScrollableParent(view);
        if (scrollParent != null) {
            scrollParent.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v,
                                           int scrollX,
                                           int scrollY,
                                           int oldScrollX,
                                           int oldScrollY) {

                    tooltipView.setTranslationY(
                            tooltipView.getTranslationY() - (scrollY - oldScrollY));
                }
            });
        }
    }

    public static ActionTooltip anchorAt(Context context, View view) {
        return new ActionTooltip(context, view);
    }

    private NestedScrollView findScrollableParent(View view) {
        if (view.getParent() == null || !(view.getParent() instanceof View)) {
            return null;
        } else if (view.getParent() instanceof NestedScrollView) {
            return ((NestedScrollView) view.getParent());
        } else {
            return findScrollableParent(((View) view.getParent()));
        }
    }

    public ActionTooltip setPositionTo(TooltipPosition position) {
        this.tooltipView.setPosition(position);
        return this;
    }

    public ActionTooltip setCustomView(View customView) {
        this.tooltipView.setCustomView(customView);
        return this;
    }

    public ActionTooltip setTextAlignment(TooltipAlign align) {
        this.tooltipView.setAlign(align);
        return this;
    }

    public TooltipActionView show() {
        final Context context = tooltipView.getContext();
        if (context != null && context instanceof Activity) {
            final ViewGroup decorView = (ViewGroup) ((Activity) context).getWindow().getDecorView();

            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Rect rect = new Rect();
                    view.getGlobalVisibleRect(rect);

                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    rect.left = location[0];

                    decorView.addView(tooltipView,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    tooltipView
                            .getViewTreeObserver()
                            .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

                                @Override
                                public boolean onPreDraw() {
                                    tooltipView.setup(rect, decorView.getWidth());
                                    tooltipView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    return false;
                                }
                            });
                }
            }, 100);
        }
        return tooltipView;
    }

    public void close() {
        tooltipView.close();
    }

    public ActionTooltip setDuration(long duration) {
        this.tooltipView.setDuration(duration);
        return this;
    }

    public ActionTooltip setBackgroundColor(@ColorInt int color) {
        this.tooltipView.setBackgroundColor(color);
        return this;
    }

    public ActionTooltip setDisplayListener(DisplayListener listener) {
        this.tooltipView.setDisplayListener(listener);
        return this;
    }

    public ActionTooltip padding(@Px int left, @Px int top, @Px int right, @Px int bottom) {
        this.tooltipView.setPadding(left, top, right, bottom);
        return this;
    }

    public ActionTooltip setAnimation(FadeAnimation tooltipAnimation) {
        this.tooltipView.setTooltipAnimation(tooltipAnimation);
        return this;
    }

    public ActionTooltip setText(String text) {
        this.tooltipView.setText(text);
        return this;
    }

    public ActionTooltip setTextColor(int textColor) {
        this.tooltipView.setTextColor(textColor);
        return this;
    }

    public ActionTooltip setTextFont(Typeface typeface) {
        this.tooltipView.setTextTypeFace(typeface);
        return this;
    }

    public ActionTooltip setTextSize(int unit, float textSize) {
        this.tooltipView.setTextSize(unit, textSize);
        return this;
    }

    public ActionTooltip setTextGravity(int textGravity) {
        this.tooltipView.setTextGravity(textGravity);
        return this;
    }

    public ActionTooltip hideOnClick(boolean clickToHide) {
        this.tooltipView.setHideOnClick(clickToHide);
        return this;
    }

    public ActionTooltip autoHide(boolean autoHide, long duration) {
        this.tooltipView.setAutoHide(autoHide);
        this.tooltipView.setDuration(duration);
        return this;
    }

    public ActionTooltip setForeverVisible(boolean foreverVisible) {
        this.tooltipView.setForeverVisible(foreverVisible);
        return this;
    }
}