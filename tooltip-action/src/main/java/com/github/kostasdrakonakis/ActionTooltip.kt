package com.github.kostasdrakonakis

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.graphics.Typeface
import android.os.Build
import android.text.InputFilter
import android.text.TextUtils
import android.text.method.MovementMethod
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.text.util.LinkifyCompat.LinkifyMask
import androidx.core.widget.NestedScrollView
import com.github.kostasdrakonakis.android.animations.FadeAnimation
import com.github.kostasdrakonakis.android.views.TooltipActionView
import com.github.kostasdrakonakis.enums.TooltipAlign
import com.github.kostasdrakonakis.enums.TooltipPosition
import com.github.kostasdrakonakis.listeners.DisplayListener

class ActionTooltip private constructor(context: Context, private val view: View) {
    private val tooltipView: TooltipActionView = TooltipActionView(context)
    fun setPositionTo(position: TooltipPosition): ActionTooltip {
        tooltipView.setPosition(position)
        return this
    }

    fun setDrawablePadding(drawablePadding: Int): ActionTooltip {
        tooltipView.setDrawablePadding(drawablePadding)
        return this
    }

    fun setDrawableLeft(@DrawableRes drawableId: Int): ActionTooltip {
        tooltipView.setDrawableLeft(drawableId)
        return this
    }

    fun setDrawableRight(@DrawableRes drawableId: Int): ActionTooltip {
        tooltipView.setDrawableRight(drawableId)
        return this
    }

    fun setDrawableTop(@DrawableRes drawableId: Int): ActionTooltip {
        tooltipView.setDrawableTop(drawableId)
        return this
    }

    fun setDrawableBottom(@DrawableRes drawableId: Int): ActionTooltip {
        tooltipView.setDrawableBottom(drawableId)
        return this
    }

    fun setCustomView(customView: View?): ActionTooltip {
        tooltipView.setCustomView(customView!!)
        return this
    }

    fun setCustomView(@LayoutRes layoutId: Int): ActionTooltip {
        tooltipView.setCustomView(layoutId)
        return this
    }

    fun setTextAlignment(align: TooltipAlign?): ActionTooltip {
        tooltipView.setAlign(align!!)
        return this
    }

    fun show(): TooltipActionView {
        val context = tooltipView.context
        if (context is Activity) {
            val decorView = context.window.decorView as ViewGroup
            view.postDelayed({
                val rect = Rect()
                view.getGlobalVisibleRect(rect)
                val location = IntArray(2)
                view.getLocationOnScreen(location)
                rect.left = location[0]
                decorView.addView(tooltipView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
                tooltipView
                    .viewTreeObserver
                    .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                        override fun onPreDraw(): Boolean {
                            tooltipView.setup(rect, decorView.width)
                            tooltipView.viewTreeObserver.removeOnPreDrawListener(this)
                            return false
                        }
                    })
            }, 100)
        }
        return tooltipView
    }

    fun close() {
        tooltipView.close()
    }

    fun setDuration(duration: Long): ActionTooltip {
        tooltipView.setDuration(duration)
        return this
    }

    fun setDuration(duration: Int): ActionTooltip {
        tooltipView.setDuration(duration.toLong())
        return this
    }

    fun setDurationId(@IntegerRes durationId: Int): ActionTooltip {
        tooltipView.setDuration(durationId.toLong())
        return this
    }

    fun setBackgroundColor(@ColorInt color: Int): ActionTooltip {
        tooltipView.setBackgroundColor(color)
        return this
    }

    fun setBackgroundColorId(@ColorRes colorId: Int): ActionTooltip {
        tooltipView.setBackgroundColorId(colorId)
        return this
    }

    fun setDisplayListener(listener: DisplayListener): ActionTooltip {
        tooltipView.setDisplayListener(listener)
        return this
    }

    fun setPadding(@Px left: Int, @Px top: Int, @Px right: Int, @Px bottom: Int): ActionTooltip {
        tooltipView.setPadding(left, top, right, bottom)
        return this
    }

    fun setPadding(@Px padding: Int): ActionTooltip {
        tooltipView.setPadding(padding, padding, padding, padding)
        return this
    }

    fun setPaddingLeft(@Px left: Int): ActionTooltip {
        tooltipView.setPadding(left, 0, 0, 0)
        return this
    }

    fun setPaddingTop(@Px top: Int): ActionTooltip {
        tooltipView.setPadding(0, top, 0, 0)
        return this
    }

    fun setPaddingRight(@Px right: Int): ActionTooltip {
        tooltipView.setPadding(0, 0, right, 0)
        return this
    }

    fun setPaddingBottom(@Px bottom: Int): ActionTooltip {
        tooltipView.setPadding(0, 0, 0, bottom)
        return this
    }

    fun setAnimation(tooltipAnimation: FadeAnimation): ActionTooltip {
        tooltipView.setTooltipAnimation(tooltipAnimation)
        return this
    }

    fun setAutoLinkMask(@LinkifyMask mask: Int): ActionTooltip {
        tooltipView.setAutoLinkMask(mask)
        return this
    }

    fun setArrowHeightId(@IntegerRes arrowHeightId: Int): ActionTooltip {
        tooltipView.setArrowHeightId(arrowHeightId)
        return this
    }

    fun setArrowHeight(arrowHeight: Int): ActionTooltip {
        tooltipView.setArrowHeight(arrowHeight)
        return this
    }

    fun setCornerRadiusId(@IntegerRes cornerRadiusId: Int): ActionTooltip {
        tooltipView.setCornerRadiusId(cornerRadiusId)
        return this
    }

    fun setCornerRadius(cornerRadius: Int): ActionTooltip {
        tooltipView.setCornerRadius(cornerRadius)
        return this
    }

    fun setAllCaps(allCaps: Boolean): ActionTooltip {
        tooltipView.setAllCaps(allCaps)
        return this
    }

    fun setEllipsize(atWhere: TextUtils.TruncateAt): ActionTooltip {
        tooltipView.setEllipsize(atWhere)
        return this
    }

    fun setEms(ems: Int): ActionTooltip {
        tooltipView.setEms(ems)
        return this
    }

    fun setFilters(filters: Array<InputFilter>): ActionTooltip {
        tooltipView.setFilters(filters)
        return this
    }

    fun setError(error: CharSequence): ActionTooltip {
        tooltipView.setError(error)
        return this
    }

    fun setError(@StringRes stringId: Int): ActionTooltip {
        tooltipView.setError(stringId)
        return this
    }

    fun setHint(hint: CharSequence): ActionTooltip {
        tooltipView.setHint(hint)
        return this
    }

    fun setHint(@StringRes hintId: Int): ActionTooltip {
        tooltipView.setHint(hintId)
        return this
    }

    fun setHintTextColor(@ColorInt color: Int): ActionTooltip {
        tooltipView.setHintTextColor(color)
        return this
    }

    fun setHintTextColor(color: String): ActionTooltip {
        tooltipView.setHintTextColor(color)
        return this
    }

    fun setHorizontallyScrolling(whether: Boolean): ActionTooltip {
        tooltipView.setHorizontallyScrolling(whether)
        return this
    }

    fun setVerticalScrollBarEnabled(verticalScrollBarEnabled: Boolean): ActionTooltip {
        tooltipView.isVerticalScrollBarEnabled = verticalScrollBarEnabled
        return this
    }

    fun setSingleLine(singleLine: Boolean): ActionTooltip {
        tooltipView.setSingleLine(singleLine)
        return this
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setElevation(elevation: Float): ActionTooltip {
        tooltipView.elevation = elevation
        return this
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setLetterSpacing(letterSpacing: Float): ActionTooltip {
        tooltipView.setLetterSpacing(letterSpacing)
        return this
    }

    fun setMaxLines(maxLines: Int): ActionTooltip {
        tooltipView.setMaxLines(maxLines)
        return this
    }

    fun setMaxWidth(maxPixels: Int): ActionTooltip {
        tooltipView.setMaxWidth(maxPixels)
        return this
    }

    fun setMinLines(minLines: Int): ActionTooltip {
        tooltipView.setMinLines(minLines)
        return this
    }

    fun setMovementMethod(movementMethod: MovementMethod): ActionTooltip {
        tooltipView.setMovementMethod(movementMethod)
        return this
    }

    fun setText(text: String?): ActionTooltip {
        tooltipView.setText(text!!)
        return this
    }

    fun setText(@StringRes stringId: Int): ActionTooltip {
        tooltipView.setText(stringId)
        return this
    }

    fun setTextColorId(@ColorRes colorId: Int): ActionTooltip {
        tooltipView.setTextColorId(colorId)
        return this
    }

    fun setTextColor(@ColorInt color: Int): ActionTooltip {
        tooltipView.setTextColor(color)
        return this
    }

    fun setTextColor(colorString: String): ActionTooltip {
        tooltipView.setTextColor(colorString)
        return this
    }

    fun setLinkTextColor(color: String): ActionTooltip {
        tooltipView.setLinkTextColor(color)
        return this
    }

    fun setLinkTextColor(@ColorInt color: Int): ActionTooltip {
        tooltipView.setLinkTextColor(color)
        return this
    }

    fun setLinkTextColorId(@ColorRes colorId: Int): ActionTooltip {
        tooltipView.setLinkTextColorId(colorId)
        return this
    }

    fun setTypeFace(typeface: Typeface?): ActionTooltip {
        tooltipView.setTypeFace(typeface)
        return this
    }

    fun setTextSize(unit: Int, textSize: Float): ActionTooltip {
        tooltipView.setTextSize(unit, textSize)
        return this
    }

    fun setTextGravity(textGravity: Int): ActionTooltip {
        tooltipView.setTextGravity(textGravity)
        return this
    }

    fun setOnClickListener(onClickListener: View.OnClickListener): ActionTooltip {
        tooltipView.setOnClickListener(onClickListener)
        return this
    }

    fun setOnLongClickListener(onLongClickListener: View.OnLongClickListener): ActionTooltip {
        tooltipView.setOnLongClickListener(onLongClickListener)
        return this
    }

    fun hideOnClick(clickToHide: Boolean): ActionTooltip {
        tooltipView.setHideOnClick(clickToHide)
        return this
    }

    fun autoHide(autoHide: Boolean, duration: Long): ActionTooltip {
        tooltipView.setAutoHide(autoHide)
        tooltipView.setDuration(duration)
        return this
    }

    fun setForeverVisible(foreverVisible: Boolean): ActionTooltip {
        tooltipView.setForeverVisible(foreverVisible)
        return this
    }

    private fun findScrollableParent(view: View): NestedScrollView? {
        return if (view.parent == null || view.parent !is View) {
            null
        } else if (view.parent is NestedScrollView) {
            view.parent as NestedScrollView
        } else {
            findScrollableParent(view.parent as View)
        }
    }

    companion object {
        fun anchorAt(context: Context, view: View): ActionTooltip {
            return ActionTooltip(context, view)
        }

        @JvmStatic
        fun anchorAt(context: Context, @IdRes viewId: Int): ActionTooltip {
            return ActionTooltip(context, (context as Activity).findViewById(viewId))
        }
    }

    init {
        val scrollParent = findScrollableParent(view)
        scrollParent?.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY -> tooltipView.translationY = tooltipView.translationY - (scrollY - oldScrollY) })
    }
}