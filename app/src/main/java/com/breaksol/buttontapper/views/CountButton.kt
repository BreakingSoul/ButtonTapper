package com.breaksol.buttontapper.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.breaksol.buttontapper.R
import info.hoang8f.widget.FButton
import kotlin.properties.Delegates

class CountButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 /*R.style.CountButton TODO add style here later maybe that would be cool as heck*/
) : FButton(context, attrs, defStyleAttr) {

    constructor(
            context: Context, listener: () -> Unit, attrs: AttributeSet? = null,
            defStyleAttr: Int = 0
    ) : this(context, attrs, defStyleAttr) {
        scoreChangedListener = listener
    }

    private var remainingClicks: Int by Delegates.observable(-1) { _, oldValue, newValue ->
        changeColor(newValue)
        if (newValue < oldValue && newValue != -1) {
            scoreChangedListener()
        }
    }

    private val listener = OnClickListener {
        if (remainingClicks > 0) {
            remainingClicks--
        }
    }

    private lateinit var scoreChangedListener: () -> Unit

    init {
        setOnClickListener(listener)

        changeColor(-1)
        width = 150
        height = 150

        isShadowEnabled = true
        shadowHeight = 10
        cornerRadius = 1000
//            paint.color = attributeArray?.getColor(
//                    R.styleable.circleview_circle_background,
//                    ContextCompat.getColor(context, android.R.color.background_dark)
//            ) ?: android.R.color.black
//            onDra
    }

    private fun changeColor(clicks: Int) {
        when (clicks) {
            -1 -> {
                buttonColor = ContextCompat.getColor(context, R.color.grey)
                shadowColor = ContextCompat.getColor(context, R.color.grey_shadow)
                isEnabled = false
            }
            0 -> {
                buttonColor = ContextCompat.getColor(context, R.color.magenta)
                shadowColor = ContextCompat.getColor(context, R.color.magenta_shadow)
            }
            1 -> {
                buttonColor = ContextCompat.getColor(context, R.color.gold)
                shadowColor = ContextCompat.getColor(context, R.color.gold_shadow)
            }
//            else -> goRed()
        }
    }

    fun lightUp() {
        remainingClicks = 1
    }

    fun enable() {
        isEnabled = true
        remainingClicks = 0
    }

    fun disable() {
        isEnabled = false
        remainingClicks = -1
    }

// https://proandroiddev.com/android-custom-views-level-2-7a0f110a2ce9

}