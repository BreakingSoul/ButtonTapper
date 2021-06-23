package com.breaksol.buttontapper.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.breaksol.buttontapper.R
import kotlin.properties.Delegates

class CountButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.style.CountButton /*TODO add style here later maybe that would be cool as heck*/
) : AppCompatButton(context, attrs, defStyleAttr) {

    constructor(
            context: Context, listener: () -> Unit, attrs: AttributeSet? = null,
                    defStyleAttr: Int = 0
    ) : this(context, attrs, defStyleAttr) {
        scoreChangedListener = listener
    }

    private var remainingClicks: Int by Delegates.observable(0) { _, oldValue, newValue ->
        changeColor(newValue)
        if (newValue < oldValue) {
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
        background =  ContextCompat.getDrawable(context, R.drawable.button_shape)
        width = 150
        height = 150
//            paint.color = attributeArray?.getColor(
//                    R.styleable.circleview_circle_background,
//                    ContextCompat.getColor(context, android.R.color.background_dark)
//            ) ?: android.R.color.black
//            onDra
    }

    private fun changeColor(clicks: Int) {
        when (clicks) {
            0 -> background =  ContextCompat.getDrawable(context, R.drawable.button_shape)
            1 -> background =  ContextCompat.getDrawable(context, R.drawable.button_shape_alive)
//            else -> goRed()
        }
    }

    fun lightUp() {
        remainingClicks++
    }

// https://proandroiddev.com/android-custom-views-level-2-7a0f110a2ce9

}