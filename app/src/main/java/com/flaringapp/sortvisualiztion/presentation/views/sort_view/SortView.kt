package com.flaringapp.sortvisualiztion.presentation.views.sort_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import com.flaringapp.sortvisualiztion.utils.DataUtils
import java.lang.Exception


class SortView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setWillNotDraw(false)
    }

    private val linePaint = Paint().apply {
        color = Color.RED
        isAntiAlias = false
        isDither = false
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.MITER
    }

    @FloatRange(from = 0.0, to = 1.0)
    var minHeight: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    @FloatRange(from = 0.0, to = 1.0)
    var maxHeight: Float = 1f
        set(value) {
            field = value
            invalidate()
        }

    private var numbers: IntArray? = null

    private var minNumber: Int = 0
    private var maxNumber: Int = 0

    fun setLineColor(@ColorInt color: Int) {
        if (linePaint.color == color) return

        linePaint.color = color
        invalidate()
    }

    fun invalidateNumbers(numbers: IntArray) {
        if (this.numbers != null) {
            if (numbers.size != this.numbers!!.size) throw Exception("Numbers size is changed")
        } else {
            if (numbers.isEmpty()) throw Exception("Numbers should tot be empty")
            minNumber = numbers.min()!!
            maxNumber = numbers.max()!!
        }

        this.numbers = numbers

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        drawLines(canvas)
        super.onDraw(canvas)
    }

    private fun drawLines(canvas: Canvas) {
        if (numbers == null) return

        val lineWidth = this.width.toFloat() / numbers!!.size
        val height = this.height.toFloat()

        for (i in numbers!!.indices) {
            canvas.drawRect(
                lineWidth * i,
                DataUtils.map(
                    numbers!![i],
                    minNumber,
                    maxNumber,
                    height * minHeight,
                    height * maxHeight
                ),
                lineWidth * (i + 1),
                height,
                linePaint
            )
        }
    }
}