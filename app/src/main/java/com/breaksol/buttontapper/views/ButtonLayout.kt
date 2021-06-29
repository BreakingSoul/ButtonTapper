package com.breaksol.buttontapper.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.breaksol.buttontapper.R
import kotlin.properties.Delegates


class ButtonLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var rows = 0
    private var columns = 0
    private lateinit var idMatrixButtons: Array<MutableList<Int>>
    private var buttons: Array<Array<CountButton>>
    private val constraintSet = ConstraintSet()
    private var lastLightnedButton: CountButton? = null

    lateinit var legitClicksTextView: TextView

    private var legitClicks: Int by Delegates.observable(0) { _, _, newValue ->
        legitClicksTextView.text = "Buttons: $newValue"
    }

    init {
        val buttonScoreChangedListener = {
            lightUpRandomButton(false)
        }

        context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.ButtonLayout,
                0, 0
        ).apply {
            try {
                rows = getInteger(R.styleable.ButtonLayout_rows, 3)
                columns = getInteger(R.styleable.ButtonLayout_columns, 4)
                buttons = Array(rows) { Array(columns) { CountButton(context, buttonScoreChangedListener) } }
            } finally {
                recycle()
            }
        }

        populateLayout()
    }

    private fun populateLayout() {
        idMatrixButtons = Array(rows) { emptyArray<Int>().toMutableList() }

        for (row in idMatrixButtons) {
            for (i in 0 until columns) {
                row.add(generateViewId())
                buttons[idMatrixButtons.indexOf(row)][i].id = row[i]
                this.addView(buttons[idMatrixButtons.indexOf(row)][i])
            }

            constraintSet.clone(this)

            if (idMatrixButtons.indexOf(row) != 0) {
                for (i in 0 until columns) {
                    constraintSet.connect(row[i], ConstraintSet.TOP, idMatrixButtons[idMatrixButtons.indexOf(row) - 1][i], ConstraintSet.BOTTOM)
                }
            }

            val idArray = row.toIntArray()
            constraintSet.createHorizontalChain(
                    ConstraintSet.PARENT_ID, ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,
                    idArray, null, ConstraintSet.CHAIN_SPREAD
            )

            constraintSet.applyTo(this)
        }

        val buttonColumnIds = MutableList(rows) { 0 }
        buttonColumnIds.clear()
        for (i in 0 until columns) {
            for (j in 0 until rows) {
                buttonColumnIds.add(idMatrixButtons[j][i])
            }
            val idVerticalArray = buttonColumnIds.toIntArray()
            constraintSet.createVerticalChain(
                    ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM,
                    idVerticalArray, null, ConstraintSet.CHAIN_SPREAD)
            buttonColumnIds.clear()
        }

        constraintSet.applyTo(this)

    }

    fun lightUpRandomButton(initialButton: Boolean) {
        val newLightenedButton = buttons.random().random()
        if (lastLightnedButton != newLightenedButton) {
            if (!initialButton) {
                legitClicks++
            }
            newLightenedButton.lightUp()
            lastLightnedButton = newLightenedButton
        } else {
            lightUpRandomButton(initialButton)
        }
    }

    fun enableButtons(disable: Boolean) {
        buttons.forEach { row ->
            row.forEach { button ->
                if (disable) {
                    button.enable()
                } else {
                    button.disable()
                }
            }
        }
    }

}