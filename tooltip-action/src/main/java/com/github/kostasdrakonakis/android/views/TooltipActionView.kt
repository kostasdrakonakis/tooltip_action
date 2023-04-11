package com.github.kostasdrakonakis.android.views

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputFilter
import android.text.TextUtils
import android.text.method.MovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.text.util.LinkifyCompat.LinkifyMask
import com.github.kostasdrakonakis.android.animations.FadeAnimation
import com.github.kostasdrakonakis.enums.TooltipAlign
import com.github.kostasdrakonakis.enums.TooltipPosition
import com.github.kostasdrakonakis.listeners.DisplayListener
import com.github.kostasdrakonakis.tooltip_action.R

class TooltipActionView(context: Context) : FrameLayout(context) {
    private var childView: View
    private var defaultColor = getDefaultColor(R.color.default_tooltip_color)
    private var bubblePath: Path? = null
    private val bubble: Paint
    private var position = TooltipPosition.BOTTOM
    private var align = TooltipAlign.CENTER
    private var hideOnClick = false
    private var autoHide = true
    private var foreverVisible = false
    private var duration = getInteger(R.integer.default_tooltip_fade_duration).toLong()
    private var arrowHeight = getInteger(R.integer.default_arrow_height)
    private var drawablePadding = getInteger(R.integer.default_drawable_padding)
    private var cornerRadius = getInteger(R.integer.default_corner_size)
    private var listener: DisplayListener? = null
    private var tooltipAnimation = FadeAnimation()
    private var viewRect: Rect? = null
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        bubblePath = drawBubble(
            RectF(
                getInteger(R.integer.default_shadow_padding).toFloat(),
                getInteger(R.integer.default_shadow_padding).toFloat(),
                (width - getInteger(R.integer.default_shadow_padding) * 2).toFloat(),
                (height - getInteger(R.integer.default_shadow_padding) * 2).toFloat()),
            cornerRadius.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bubblePath?.let {
            canvas.drawPath(it, bubble)
        }
    }

    fun setCustomView(customView: View) {
        removeView(childView)
        childView = customView
        addView(childView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        postInvalidate()
        val colorDrawable = customView.background as ColorDrawable?
        if (colorDrawable != null) {
            defaultColor = colorDrawable.color
        }
        bubble.color = defaultColor
    }

    fun setCustomView(@LayoutRes layoutId: Int) {
        val layoutInflater = LayoutInflater.from(context)
        val customView = layoutInflater.inflate(layoutId, null)
        setCustomView(customView)
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        defaultColor = color
        bubble.color = color
        postInvalidate()
    }

    fun setBackgroundColorId(@ColorRes colorId: Int) {
        defaultColor = ContextCompat.getColor(context, colorId)
        bubble.color = defaultColor
        postInvalidate()
    }

    fun setPosition(position: TooltipPosition) {
        this.position = position
        val paddingTop = 20
        val paddingBottom = 30
        val paddingRight = 30
        val paddingLeft = 30
        when (position) {
            TooltipPosition.TOP -> setPadding(paddingLeft,
                paddingTop,
                paddingRight,
                paddingBottom + arrowHeight)
            TooltipPosition.BOTTOM -> setPadding(paddingLeft,
                paddingTop + arrowHeight,
                paddingRight,
                paddingBottom)
            TooltipPosition.START -> setPadding(paddingLeft,
                paddingTop,
                paddingRight + arrowHeight,
                paddingBottom)
            TooltipPosition.END -> setPadding(paddingLeft + arrowHeight,
                paddingTop,
                paddingRight,
                paddingBottom)
        }
        postInvalidate()
    }

    fun setAlign(align: TooltipAlign) {
        this.align = align
        postInvalidate()
    }

    fun setAllCaps(allCaps: Boolean) {
        if (childView is TextView) {
            (childView as TextView).isAllCaps = allCaps
        }
        postInvalidate()
    }

    fun setAutoLinkMask(@LinkifyMask mask: Int) {
        if (childView is TextView) {
            (childView as TextView).autoLinkMask = mask
        }
        postInvalidate()
    }

    fun setArrowHeightId(@IntegerRes arrowHeightId: Int) {
        arrowHeight = getInteger(arrowHeightId)
        postInvalidate()
    }

    fun setArrowHeight(arrowHeight: Int) {
        this.arrowHeight = arrowHeight
        postInvalidate()
    }

    fun setCornerRadiusId(@IntegerRes cornerRadiusId: Int) {
        cornerRadius = getInteger(cornerRadiusId)
        postInvalidate()
    }

    fun setCornerRadius(cornerRadius: Int) {
        this.cornerRadius = cornerRadius
        postInvalidate()
    }

    fun setDrawablePadding(drawablePadding: Int) {
        this.drawablePadding = drawablePadding
        postInvalidate()
    }

    fun setDrawableLeft(@DrawableRes drawableId: Int) {
        val drawable = getDrawable(drawableId)
        drawable?.setBounds(
            0,
            0,
            getInteger(R.integer.default_drawable_right_bound),
            getInteger(R.integer.default_drawable_bottom_bound))
        if (childView is TextView) {
            (childView as TextView).setCompoundDrawables(drawable, null, null, null)
            (childView as TextView).compoundDrawablePadding = drawablePadding
        }
        postInvalidate()
    }

    fun setDrawableRight(@DrawableRes drawableId: Int) {
        val drawable = getDrawable(drawableId)
        drawable?.setBounds(
            0,
            0,
            getInteger(R.integer.default_drawable_right_bound),
            getInteger(R.integer.default_drawable_bottom_bound))
        if (childView is TextView) {
            (childView as TextView).setCompoundDrawables(null, null, drawable, null)
            (childView as TextView).compoundDrawablePadding = drawablePadding
        }
        postInvalidate()
    }

    fun setDrawableTop(@DrawableRes drawableId: Int) {
        val drawable = getDrawable(drawableId)
        drawable?.setBounds(
            0,
            0,
            getInteger(R.integer.default_drawable_right_bound),
            getInteger(R.integer.default_drawable_bottom_bound))
        if (childView is TextView) {
            (childView as TextView).setCompoundDrawables(null, drawable, null, null)
            (childView as TextView).compoundDrawablePadding = drawablePadding
        }
        postInvalidate()
    }

    fun setDrawableBottom(@DrawableRes drawableId: Int) {
        val drawable = getDrawable(drawableId)
        drawable?.setBounds(
            0,
            0,
            getInteger(R.integer.default_drawable_right_bound),
            getInteger(R.integer.default_drawable_bottom_bound))
        if (childView is TextView) {
            (childView as TextView).setCompoundDrawables(null, null, null, drawable)
            (childView as TextView).compoundDrawablePadding = drawablePadding
        }
        postInvalidate()
    }

    fun setEllipsize(atWhere: TextUtils.TruncateAt) {
        if (childView is TextView) {
            (childView as TextView).ellipsize = atWhere
        }
        postInvalidate()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun setElevation(elevation: Float) {
        childView.elevation = elevation
        postInvalidate()
    }

    fun setEms(ems: Int) {
        if (childView is TextView) {
            (childView as TextView).setEms(ems)
        }
        postInvalidate()
    }

    fun setFilters(filters: Array<InputFilter>) {
        if (childView is TextView) {
            (childView as TextView).filters = filters
        }
        postInvalidate()
    }

    fun setError(error: CharSequence) {
        if (childView is TextView) {
            (childView as TextView).error = error
        }
        postInvalidate()
    }

    fun setError(@StringRes stringId: Int) {
        if (childView is TextView) {
            (childView as TextView).error = context.getString(stringId)
        }
        postInvalidate()
    }

    fun setHint(hint: CharSequence) {
        if (childView is TextView) {
            (childView as TextView).hint = hint
        }
        postInvalidate()
    }

    fun setHint(@StringRes hintId: Int) {
        if (childView is TextView) {
            (childView as TextView).setHint(hintId)
        }
        postInvalidate()
    }

    fun setHintTextColor(@ColorInt color: Int) {
        if (childView is TextView) {
            (childView as TextView).setHintTextColor(color)
        }
        postInvalidate()
    }

    fun setHintTextColor(color: String) {
        if (childView is TextView) {
            (childView as TextView).setHintTextColor(Color.parseColor(color))
        }
        postInvalidate()
    }

    fun setHorizontallyScrolling(whether: Boolean) {
        if (childView is TextView) {
            (childView as TextView).setHorizontallyScrolling(whether)
        }
        postInvalidate()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    fun setLetterSpacing(letterSpacing: Float) {
        if (childView is TextView) {
            (childView as TextView).letterSpacing = letterSpacing
        }
        postInvalidate()
    }

    fun setSingleLine(singleLine: Boolean) {
        if (childView is TextView) {
            (childView as TextView).isSingleLine = singleLine
        }
        postInvalidate()
    }

    override fun setVerticalScrollBarEnabled(verticalScrollBarEnabled: Boolean) {
        childView.isVerticalScrollBarEnabled = verticalScrollBarEnabled
        postInvalidate()
    }

    fun setMaxLines(maxLines: Int) {
        if (childView is TextView) {
            (childView as TextView).maxLines = maxLines
        }
        postInvalidate()
    }

    fun setMinLines(minLines: Int) {
        if (childView is TextView) {
            (childView as TextView).minLines = minLines
        }
        postInvalidate()
    }

    fun setMovementMethod(movementMethod: MovementMethod) {
        if (childView is TextView) {
            (childView as TextView).movementMethod = movementMethod
        }
        postInvalidate()
    }

    fun setMaxWidth(maxPixels: Int) {
        if (childView is TextView) {
            (childView as TextView).maxWidth = maxPixels
        }
        postInvalidate()
    }

    fun setText(text: String) {
        if (childView is TextView) {
            (childView as TextView).text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
        postInvalidate()
    }

    fun setText(@StringRes stringId: Int) {
        if (childView is TextView) {
            (childView as TextView).text = context.getString(stringId)
        }
        postInvalidate()
    }

    fun setTextColorId(@ColorRes color: Int) {
        if (childView is TextView) {
            (childView as TextView).setTextColor(ContextCompat.getColor(context, color))
        }
        postInvalidate()
    }

    fun setTextColor(@ColorInt color: Int) {
        if (childView is TextView) {
            (childView as TextView).setTextColor(color)
        }
        postInvalidate()
    }

    fun setTextColor(color: String) {
        if (childView is TextView) {
            (childView as TextView).setTextColor(Color.parseColor(color))
        }
        postInvalidate()
    }

    fun setLinkTextColor(color: String) {
        if (childView is TextView) {
            (childView as TextView).setLinkTextColor(Color.parseColor(color))
        }
        postInvalidate()
    }

    fun setLinkTextColor(@ColorInt color: Int) {
        if (childView is TextView) {
            (childView as TextView).setLinkTextColor(color)
        }
        postInvalidate()
    }

    fun setLinkTextColorId(@ColorRes colorId: Int) {
        if (childView is TextView) {
            (childView as TextView).setLinkTextColor(ContextCompat.getColor(context, colorId))
        }
        postInvalidate()
    }

    fun setTypeFace(textTypeFace: Typeface?) {
        if (childView is TextView) {
            (childView as TextView).typeface = textTypeFace
        }
        postInvalidate()
    }

    fun setTextSize(unit: Int, size: Float) {
        if (childView is TextView) {
            (childView as TextView).setTextSize(unit, size)
        }
        postInvalidate()
    }

    fun setTextGravity(textGravity: Int) {
        if (childView is TextView) {
            (childView as TextView).gravity = textGravity
        }
        postInvalidate()
    }

    fun setHideOnClick(hideOnClick: Boolean) {
        this.hideOnClick = hideOnClick
    }

    fun setDisplayListener(listener: DisplayListener) {
        this.listener = listener
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        if (childView is TextView) childView.setOnClickListener(onClickListener)
        postInvalidate()
    }

    override fun setOnLongClickListener(onLongClickListener: OnLongClickListener?) {
        if (childView is TextView) childView.setOnLongClickListener(onLongClickListener)
        postInvalidate()
    }

    fun setTooltipAnimation(tooltipAnimation: FadeAnimation) {
        this.tooltipAnimation = tooltipAnimation
    }

    fun hide() {
        startExitAnimation(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                removeNow()
            }
        })
    }

    fun setDuration(duration: Long) {
        this.duration = duration
    }

    fun setAutoHide(autoHide: Boolean) {
        this.autoHide = autoHide
    }

    fun setForeverVisible(foreverVisible: Boolean) {
        this.foreverVisible = foreverVisible
    }

    private fun setupPosition(rectangle: Rect) {
        val horizontal: Int
        val vertical: Int
        when (position) {
            TooltipPosition.START, TooltipPosition.END -> {
                horizontal = if (position == TooltipPosition.START) rectangle.left - width else rectangle.right
                vertical = rectangle.top + getAlignOffset(height, rectangle.height())
            }
            else -> {
                vertical = if (position == TooltipPosition.BOTTOM) rectangle.bottom else rectangle.top - height
                horizontal = rectangle.left + getAlignOffset(width, rectangle.width())
            }
        }
        translationX = horizontal.toFloat()
        translationY = vertical.toFloat()
    }

    private fun adjustSize(rect: Rect, screenWidth: Int): Boolean {
        val r = Rect()
        getGlobalVisibleRect(r)
        var changed = false
        val layoutParams = layoutParams
        if (position == TooltipPosition.START && width > rect.left) {
            layoutParams.width = rect.left - getInteger(R.integer.default_screen_margin)
            changed = true
        } else if (position == TooltipPosition.END && rect.right + width > screenWidth) {
            layoutParams.width = screenWidth - rect.right - getInteger(R.integer.default_screen_margin)
            changed = true
        } else if (position == TooltipPosition.TOP || position == TooltipPosition.BOTTOM) {
            var adjustedLeft = rect.left
            var adjustedRight = rect.right
            if (rect.centerX() + width / 2f > screenWidth) {
                val diff = rect.centerX() + width / 2f - screenWidth
                adjustedLeft -= diff.toInt()
                adjustedRight -= diff.toInt()
                setAlign(TooltipAlign.CENTER)
                changed = true
            } else if (rect.centerX() - width / 2f < 0) {
                val diff = -(rect.centerX() - width / 2f)
                adjustedLeft += diff.toInt()
                adjustedRight += diff.toInt()
                setAlign(TooltipAlign.CENTER)
                changed = true
            }
            if (adjustedLeft < 0) {
                adjustedLeft = 0
            }
            if (adjustedRight > screenWidth) {
                adjustedRight = screenWidth
            }
            rect.left = adjustedLeft
            rect.right = adjustedRight
        }
        setLayoutParams(layoutParams)
        postInvalidate()
        return changed
    }

    fun setup(viewRect: Rect?, screenWidth: Int) {
        this.viewRect = Rect(viewRect)
        val myRect = Rect(viewRect)
        val changed = adjustSize(myRect, screenWidth)
        if (!changed) {
            onSetup(myRect)
        } else {
            viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    onSetup(myRect)
                    viewTreeObserver.removeOnPreDrawListener(this)
                    return false
                }
            })
        }
    }

    fun close() {
        hide()
    }

    private fun startEnterAnimation() {
        tooltipAnimation.onAnimationEntered(this, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                if (listener != null) listener!!.onViewDisplayed(this@TooltipActionView)
            }
        })
    }

    private fun startExitAnimation(animatorListener: Animator.AnimatorListener) {
        tooltipAnimation.onAnimationExited(this, object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animatorListener.onAnimationEnd(animation)
                if (listener != null) listener!!.onViewHidden(this@TooltipActionView)
            }
        })
    }

    private fun handleAutoRemove() {
        if (hideOnClick) {
            setOnClickListener {
                if (hideOnClick) {
                    hide()
                }
            }
        }
        if (autoHide && !foreverVisible) {
            postDelayed({ hide() }, duration)
        }
    }

    private fun getAlignOffset(myLength: Int, hisLength: Int): Int {
        return when (align) {
            TooltipAlign.END -> hisLength - myLength
            TooltipAlign.CENTER -> (hisLength - myLength) / 2
            else -> 0
        }
    }

    private fun drawBubble(myRect: RectF,
                           topLeft: Float,
                           topRight: Float,
                           bottomRight: Float,
                           bottomLeft: Float): Path {
        var topLeftDiameter = topLeft
        var topRightDiameter = topRight
        var bottomRightDiameter = bottomRight
        var bottomLeftDiameter = bottomLeft
        val path = Path()
        if (viewRect == null) return path
        topLeftDiameter = if (topLeftDiameter < 0) 0F else topLeft
        topRightDiameter = if (topRightDiameter < 0) 0F else topRight
        bottomLeftDiameter = if (bottomLeftDiameter < 0) 0F else bottomRight
        bottomRightDiameter = if (bottomRightDiameter < 0) 0F else bottomLeft
        val spacingLeft = if (position == TooltipPosition.END) arrowHeight.toFloat() else 0F
        val spacingTop = if (position == TooltipPosition.BOTTOM) arrowHeight.toFloat() else 0F
        val spacingRight = if (position == TooltipPosition.START) arrowHeight.toFloat() else 0F
        val spacingBottom = if (position == TooltipPosition.TOP) arrowHeight.toFloat() else 0F
        val left = spacingLeft + myRect.left
        val top = spacingTop + myRect.top
        val right = myRect.right - spacingRight
        val bottom = myRect.bottom - spacingBottom
        val centerX = viewRect!!.centerX() - x
        path.moveTo(left + topLeftDiameter / 2f, top)
        //LEFT, TOP
        val arrowWidth = 15
        if (position == TooltipPosition.BOTTOM) {
            path.lineTo(centerX - arrowWidth, top)
            path.lineTo(centerX, myRect.top)
            path.lineTo(centerX + arrowWidth, top)
        }
        path.lineTo(right - topRightDiameter / 2f, top)
        path.quadTo(right, top, right, top + topRightDiameter / 2)
        //RIGHT, TOP
        if (position == TooltipPosition.START) {
            path.lineTo(right, bottom / 2f - arrowWidth)
            path.lineTo(myRect.right, bottom / 2f)
            path.lineTo(right, bottom / 2f + arrowWidth)
        }
        path.lineTo(right, bottom - bottomRightDiameter / 2)
        path.quadTo(right, bottom, right - bottomRightDiameter / 2, bottom)
        //RIGHT, BOTTOM
        if (position == TooltipPosition.TOP) {
            path.lineTo(centerX + arrowWidth, bottom)
            path.lineTo(centerX, myRect.bottom)
            path.lineTo(centerX - arrowWidth, bottom)
        }
        path.lineTo(left + bottomLeftDiameter / 2, bottom)
        path.quadTo(left, bottom, left, bottom - bottomLeftDiameter / 2)
        //LEFT, BOTTOM
        if (position == TooltipPosition.END) {
            path.lineTo(left, bottom / 2f + arrowWidth)
            path.lineTo(myRect.left, bottom / 2f)
            path.lineTo(left, bottom / 2f - arrowWidth)
        }
        path.lineTo(left, top + topLeftDiameter / 2)
        path.quadTo(left, top, left + topLeftDiameter / 2, top)
        path.close()
        return path
    }

    private fun onSetup(rect: Rect) {
        setupPosition(rect)
        bubblePath = drawBubble(
            RectF(getInteger(R.integer.default_shadow_padding).toFloat(),
                getInteger(R.integer.default_shadow_padding).toFloat(),
                width - getInteger(R.integer.default_shadow_padding) * 2f,
                height - getInteger(R.integer.default_shadow_padding) * 2f),
            cornerRadius.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat(), cornerRadius.toFloat())
        startEnterAnimation()
        handleAutoRemove()
    }

    private fun removeNow() {
        post {
            if (parent != null) {
                val parent = parent as ViewGroup
                parent.removeView(this@TooltipActionView)
            }
        }
    }

    private fun getInteger(@IntegerRes id: Int): Int {
        return context.resources.getInteger(id)
    }

    private fun getDrawable(@DrawableRes drawableId: Int): Drawable? {
        val resources = context.resources
        return ResourcesCompat.getDrawable(resources, drawableId, null)
    }

    private fun getDefaultColor(@ColorRes defaultColorId: Int): Int {
        return ContextCompat.getColor(context, defaultColorId)
    }

    init {
        setWillNotDraw(false)
        childView = TextView(context)
        (childView as TextView).setTextColor(Color.WHITE)
        addView(childView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        childView.setPadding(0, 0, 0, 0)
        bubble = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = defaultColor
            style = Paint.Style.FILL
        }
        setLayerType(View.LAYER_TYPE_SOFTWARE, bubble)
    }
}