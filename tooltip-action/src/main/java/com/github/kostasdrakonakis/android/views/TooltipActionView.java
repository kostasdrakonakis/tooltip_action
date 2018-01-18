package com.github.kostasdrakonakis.android.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.kostasdrakonakis.android.animations.FadeAnimation;
import com.github.kostasdrakonakis.enums.TooltipAlign;
import com.github.kostasdrakonakis.enums.TooltipPosition;
import com.github.kostasdrakonakis.listeners.DisplayListener;
import com.github.kostasdrakonakis.tooltip_action.R;

public class TooltipActionView extends FrameLayout {

    protected View childView;
    private int defaultColor = Color.parseColor("#FF4081");
    private Path bubblePath;
    private Paint bubble;
    private TooltipPosition position = TooltipPosition.BOTTOM;
    private TooltipAlign align = TooltipAlign.CENTER;
    private boolean hideOnClick;
    private boolean autoHide = true;
    private boolean foreverVisible = false;
    private long duration = getInteger(R.integer.default_tooltip_fade_duration);
    private int arrowHeight = getInteger(R.integer.default_arrow_height);

    private DisplayListener listener;

    private FadeAnimation tooltipAnimation = new FadeAnimation();

    private Rect viewRect;

    public TooltipActionView(Context context) {
        super(context);
        setWillNotDraw(false);

        this.childView = new TextView(context);
        ((TextView) childView).setTextColor(Color.WHITE);
        addView(childView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        childView.setPadding(0, 0, 0, 0);

        bubble = new Paint(Paint.ANTI_ALIAS_FLAG);
        bubble.setColor(defaultColor);
        bubble.setStyle(Paint.Style.FILL);

        setLayerType(LAYER_TYPE_SOFTWARE, bubble);
    }

    public void setCustomView(View customView) {
        this.removeView(childView);
        this.childView = customView;
        addView(childView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        postInvalidate();
        ColorDrawable colorDrawable = (ColorDrawable) customView.getBackground();
        if (colorDrawable != null) {
            defaultColor = colorDrawable.getColor();
        }
        bubble.setColor(defaultColor);
    }

    public void setCustomView(@LayoutRes int layoutId) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View customView = layoutInflater.inflate(layoutId, null);
        setCustomView(customView);
    }

    public void setBackgroundColor(@ColorInt int color) {
        this.defaultColor = color;
        bubble.setColor(color);
        postInvalidate();
    }

    public void setBackgroundColorId(@ColorRes int colorId) {
        this.defaultColor = ContextCompat.getColor(getContext(), colorId);
        bubble.setColor(defaultColor);
        postInvalidate();
    }

    public void setPosition(TooltipPosition position) {
        this.position = position;
        int paddingTop = 20;
        int paddingBottom = 30;
        int paddingRight = 30;
        int paddingLeft = 30;

        switch (position) {
            case TOP:
                setPadding(paddingLeft,
                        paddingTop,
                        paddingRight,
                        paddingBottom + arrowHeight);
                break;
            case BOTTOM:
                setPadding(paddingLeft,
                        paddingTop + arrowHeight,
                        paddingRight,
                        paddingBottom);
                break;
            case START:
                setPadding(paddingLeft,
                        paddingTop,
                        paddingRight + arrowHeight,
                        paddingBottom);
                break;
            case END:
                setPadding(paddingLeft + arrowHeight,
                        paddingTop,
                        paddingRight,
                        paddingBottom);
                break;
        }
        postInvalidate();
    }

    public void setAlign(TooltipAlign align) {
        this.align = align;
        postInvalidate();
    }

    public void setAllCaps(boolean allCaps) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setAllCaps(allCaps);
        }
        postInvalidate();
    }

    public void setArrowHeightId(@IntegerRes int arrowHeightId) {
        this.arrowHeight = getInteger(arrowHeightId);
        postInvalidate();
    }

    public void setArrowHeight(int arrowHeight) {
        this.arrowHeight = arrowHeight;
        postInvalidate();
    }

    public void setEllipsize(TextUtils.TruncateAt atWhere) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setEllipsize(atWhere);
        }
        postInvalidate();
    }

    public void setEms(int ems) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setEms(ems);
        }
        postInvalidate();
    }

    public void setFilters(InputFilter[] filters) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setFilters(filters);
        }
        postInvalidate();
    }

    public void setError(CharSequence error) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setError(error);
        }
        postInvalidate();
    }

    public void setHint(CharSequence hint) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setHint(hint);
        }
        postInvalidate();
    }

    public void setHint(@StringRes int hintId) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setHint(hintId);
        }
        postInvalidate();
    }

    public void setHintTextColor(@ColorInt int color) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setHintTextColor(color);
        }
        postInvalidate();
    }

    public void setHorizontallyScrolling(boolean whether) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setHorizontallyScrolling(whether);
        }
        postInvalidate();
    }

    public void setSingleLine(boolean singleLine) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setSingleLine(singleLine);
        }
        postInvalidate();
    }

    public void setVerticalScrollBarEnabled(boolean verticalScrollBarEnabled) {
        this.childView.setVerticalScrollBarEnabled(verticalScrollBarEnabled);
        postInvalidate();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setElevation(float elevation) {
        this.childView.setElevation(elevation);
        postInvalidate();
    }

    public void setMaxLines(int maxLines) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setMaxLines(maxLines);
        }
        postInvalidate();
    }

    public void setMinLines(int minLines) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setMinLines(minLines);
        }
        postInvalidate();
    }

    public void setMovementMethod(MovementMethod movementMethod) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setMovementMethod(movementMethod);
        }
        postInvalidate();
    }

    public void setMaxWidth(int maxPixels) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setMaxWidth(maxPixels);
        }
        postInvalidate();
    }

    public void setText(@NonNull String text) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setText(Html.fromHtml(text));
        }
        postInvalidate();
    }

    public void setText(@StringRes int stringId) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setText(getContext().getString(stringId));
        }
        postInvalidate();
    }

    public void setTextColorId(@ColorRes int color) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setTextColor(ContextCompat.getColor(getContext(), color));
        }
        postInvalidate();
    }

    public void setTextColor(@ColorInt int color) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setTextColor(color);
        }
        postInvalidate();
    }

    public void setTextColor(@NonNull String color) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setTextColor(Color.parseColor(color));
        }
        postInvalidate();
    }

    public void setTypeFace(Typeface textTypeFace) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setTypeface(textTypeFace);
        }
        postInvalidate();
    }

    public void setTextSize(int unit, float size) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setTextSize(unit, size);
        }
        postInvalidate();
    }

    public void setTextGravity(int textGravity) {
        if (childView instanceof TextView) {
            ((TextView) this.childView).setGravity(textGravity);
        }
        postInvalidate();
    }

    public void setHideOnClick(boolean hideOnClick) {
        this.hideOnClick = hideOnClick;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        bubblePath = drawBubble(
                new RectF(
                        getInteger(R.integer.default_shadow_padding),
                        getInteger(R.integer.default_shadow_padding),
                        width - getInteger(R.integer.default_shadow_padding) * 2,
                        height - getInteger(R.integer.default_shadow_padding) * 2),
                getInteger(R.integer.default_corner_size),
                getInteger(R.integer.default_corner_size),
                getInteger(R.integer.default_corner_size),
                getInteger(R.integer.default_corner_size));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bubblePath != null) {
            canvas.drawPath(bubblePath, bubble);
        }
    }

    public void setDisplayListener(DisplayListener listener) {
        this.listener = listener;
    }

    public void setTooltipAnimation(FadeAnimation tooltipAnimation) {
        this.tooltipAnimation = tooltipAnimation;
    }

    protected void startEnterAnimation() {
        tooltipAnimation.onAnimationEntered(this, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listener != null) listener.onViewDisplayed(TooltipActionView.this);
            }
        });
    }

    protected void startExitAnimation(final Animator.AnimatorListener animatorListener) {
        tooltipAnimation.onAnimationExited(this, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorListener.onAnimationEnd(animation);
                if (listener != null) listener.onViewHidden(TooltipActionView.this);
            }
        });
    }

    protected void handleAutoRemove() {
        if (hideOnClick) {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (hideOnClick) {
                        hide();
                    }
                }
            });
        }

        if (autoHide && !foreverVisible) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    hide();
                }
            }, duration);
        }
    }

    public void hide() {
        startExitAnimation(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeNow();
            }
        });
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
    }

    public void setForeverVisible(boolean foreverVisible) {
        this.foreverVisible = foreverVisible;
    }

    public void setupPosition(Rect rectangle) {

        int horizontal, vertical;

        switch (position) {
            case START:
            case END:
                horizontal = position == TooltipPosition.START
                        ? rectangle.left - getWidth()
                        : rectangle.right;
                vertical = rectangle.top + getAlignOffset(getHeight(), rectangle.height());
                break;
            default:
                vertical = position == TooltipPosition.BOTTOM
                        ? rectangle.bottom
                        : rectangle.top - getHeight();
                horizontal = rectangle.left + getAlignOffset(getWidth(), rectangle.width());
                break;
        }

        setTranslationX(horizontal);
        setTranslationY(vertical);
    }

    private int getAlignOffset(int myLength, int hisLength) {
        switch (align) {
            case END:
                return hisLength - myLength;
            case CENTER:
                return (hisLength - myLength) / 2;
        }
        return 0;
    }

    private Path drawBubble(RectF myRect,
                            float topLeftDiameter,
                            float topRightDiameter,
                            float bottomRightDiameter,
                            float bottomLeftDiameter) {

        final Path path = new Path();

        if (viewRect == null)
            return path;

        topLeftDiameter = topLeftDiameter < 0 ? 0 : topLeftDiameter;
        topRightDiameter = topRightDiameter < 0 ? 0 : topRightDiameter;
        bottomLeftDiameter = bottomLeftDiameter < 0 ? 0 : bottomLeftDiameter;
        bottomRightDiameter = bottomRightDiameter < 0 ? 0 : bottomRightDiameter;

        final float spacingLeft = this.position == TooltipPosition.END
                ? arrowHeight
                : 0;
        final float spacingTop = this.position == TooltipPosition.BOTTOM
                ? arrowHeight
                : 0;
        final float spacingRight = this.position == TooltipPosition.START
                ? arrowHeight
                : 0;
        final float spacingBottom = this.position == TooltipPosition.TOP
                ? arrowHeight
                : 0;

        final float left = spacingLeft + myRect.left;
        final float top = spacingTop + myRect.top;
        final float right = myRect.right - spacingRight;
        final float bottom = myRect.bottom - spacingBottom;
        final float centerX = viewRect.centerX() - getX();

        path.moveTo(left + topLeftDiameter / 2f, top);
        //LEFT, TOP

        int ARROW_WIDTH = 15;
        if (position == TooltipPosition.BOTTOM) {
            path.lineTo(centerX - ARROW_WIDTH, top);
            path.lineTo(centerX, myRect.top);
            path.lineTo(centerX + ARROW_WIDTH, top);
        }
        path.lineTo(right - topRightDiameter / 2f, top);

        path.quadTo(right, top, right, top + topRightDiameter / 2);
        //RIGHT, TOP

        if (position == TooltipPosition.START) {
            path.lineTo(right, bottom / 2f - ARROW_WIDTH);
            path.lineTo(myRect.right, bottom / 2f);
            path.lineTo(right, bottom / 2f + ARROW_WIDTH);
        }
        path.lineTo(right, bottom - bottomRightDiameter / 2);

        path.quadTo(right, bottom, right - bottomRightDiameter / 2, bottom);
        //RIGHT, BOTTOM

        if (position == TooltipPosition.TOP) {
            path.lineTo(centerX + ARROW_WIDTH, bottom);
            path.lineTo(centerX, myRect.bottom);
            path.lineTo(centerX - ARROW_WIDTH, bottom);
        }
        path.lineTo(left + bottomLeftDiameter / 2, bottom);

        path.quadTo(left, bottom, left, bottom - bottomLeftDiameter / 2);
        //LEFT, BOTTOM

        if (position == TooltipPosition.END) {
            path.lineTo(left, bottom / 2f + ARROW_WIDTH);
            path.lineTo(myRect.left, bottom / 2f);
            path.lineTo(left, bottom / 2f - ARROW_WIDTH);
        }
        path.lineTo(left, top + topLeftDiameter / 2);

        path.quadTo(left, top, left + topLeftDiameter / 2, top);

        path.close();

        return path;
    }

    public boolean adjustSize(Rect rect, int screenWidth) {

        final Rect r = new Rect();
        getGlobalVisibleRect(r);

        boolean changed = false;
        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (position == TooltipPosition.START && getWidth() > rect.left) {
            layoutParams.width = rect.left - getInteger(R.integer.default_screen_margin);
            changed = true;
        } else if (position == TooltipPosition.END && rect.right + getWidth() > screenWidth) {
            layoutParams.width =
                    screenWidth - rect.right - getInteger(R.integer.default_screen_margin);
            changed = true;
        } else if (position == TooltipPosition.TOP || position == TooltipPosition.BOTTOM) {
            int adjustedLeft = rect.left;
            int adjustedRight = rect.right;

            if ((rect.centerX() + getWidth() / 2f) > screenWidth) {
                float diff = (rect.centerX() + getWidth() / 2f) - screenWidth;

                adjustedLeft -= diff;
                adjustedRight -= diff;

                setAlign(TooltipAlign.CENTER);
                changed = true;
            } else if ((rect.centerX() - getWidth() / 2f) < 0) {
                float diff = -(rect.centerX() - getWidth() / 2f);

                adjustedLeft += diff;
                adjustedRight += diff;

                setAlign(TooltipAlign.CENTER);
                changed = true;
            }

            if (adjustedLeft < 0) {
                adjustedLeft = 0;
            }

            if (adjustedRight > screenWidth) {
                adjustedRight = screenWidth;
            }

            rect.left = adjustedLeft;
            rect.right = adjustedRight;
        }

        setLayoutParams(layoutParams);
        postInvalidate();
        return changed;
    }

    private void onSetup(Rect rect) {
        setupPosition(rect);

        bubblePath = drawBubble(
                new RectF(getInteger(R.integer.default_shadow_padding),
                        getInteger(R.integer.default_shadow_padding),
                        getWidth() - getInteger(R.integer.default_shadow_padding) * 2f,
                        getHeight() - getInteger(R.integer.default_shadow_padding) * 2f),
                getInteger(R.integer.default_corner_size),
                getInteger(R.integer.default_corner_size),
                getInteger(R.integer.default_corner_size),
                getInteger(R.integer.default_corner_size));

        startEnterAnimation();

        handleAutoRemove();
    }

    public void setup(final Rect viewRect, int screenWidth) {
        this.viewRect = new Rect(viewRect);
        final Rect myRect = new Rect(viewRect);

        final boolean changed = adjustSize(myRect, screenWidth);
        if (!changed) {
            onSetup(myRect);
        } else {
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    onSetup(myRect);
                    getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        }
    }

    public void close() {
        hide();
    }

    private void removeNow() {
        if (getParent() != null) {
            final ViewGroup parent = ((ViewGroup) getParent());
            parent.removeView(TooltipActionView.this);
        }
    }

    private int getInteger(@IntegerRes int id) {
        return getContext().getResources().getInteger(id);
    }
}
