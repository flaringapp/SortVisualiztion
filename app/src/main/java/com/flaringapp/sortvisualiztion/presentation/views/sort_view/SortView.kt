package com.flaringapp.sortvisualiztion.presentation.views.sort_view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import com.flaringapp.sortvisualiztion.presentation.views.sort_view.models.LinesData
import com.flaringapp.sortvisualiztion.utils.DataUtils
import kotlin.math.absoluteValue

class SortView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        addOnLayoutChangeListener { _, left, top, right, bottom, _, _, _, _ ->
            invalidateDrawData((right - left).absoluteValue, (bottom - top).absoluteValue)
        }
    }

    @ColorInt
    var fillColor: Int = Color.BLACK
        set(color) {
            field = color
            invalidate()
        }

    private lateinit var linesData: LinesData

    private lateinit var drawBitmap: Bitmap
    private lateinit var drawCanvas: Canvas

    private var lineWidth: Float = 0f
    private var minLineHeight: Float = 0f
    private var maxLineHeight: Float = 0f

    private val linePaint = Paint().apply {
        color = Color.RED
        isAntiAlias = false
        isDither = false
        style = Paint.Style.FILL
        strokeJoin = Paint.Join.MITER
    }

    fun setData(linesData: LinesData) {
        this.linesData = linesData
        invalidateDrawData()
    }

    fun setLineColor(@ColorInt color: Int) {
        if (linePaint.color == color) return

        linePaint.color = color
        invalidate()
    }

    fun notifyMove(from: Int, to: Int) {
        if (from == to) return

        linesData.numbers.swap(from, to)

        drawSwapLines(from, to)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(drawBitmap, 0f, 0f, null)
    }

    private fun invalidateDrawData() {
        invalidateDrawData(measuredWidth, measuredHeight)
    }

    private fun invalidateDrawData(width: Int, height: Int) {
        if (width <= 0 || height <= 0) return

        if (::drawBitmap.isInitialized) {
            drawBitmap.reconfigure(width, height, Bitmap.Config.RGB_565)
        } else {
            drawBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            drawCanvas = Canvas(drawBitmap)
        }

        lineWidth = width.toFloat() / linesData.numbers.size
        minLineHeight = height * linesData.minHeight
        maxLineHeight = height * linesData.maxHeight

        drawInitialLines()

        invalidate()
    }

    private fun drawInitialLines() {
        drawCanvas.drawColor(fillColor)
        val height = measuredHeight.toFloat()
        linesData.numbers.forEachIndexed { index, number ->
            val startX = index * lineWidth
            val startY = DataUtils.map(
                number,
                linesData.numberMin,
                linesData.numberMax,
                minLineHeight,
                maxLineHeight
            )
            drawCanvas.drawRect(
                startX,
                startY,
                startX + lineWidth,
                height,
                linePaint
            )
        }
    }

    private fun drawSwapLines(first: Int, second: Int) {
        val clearPaint = Paint(linePaint)
        clearPaint.color = fillColor

        val startXFirst = first * lineWidth
        val startXSecond = second * lineWidth
        val height = measuredHeight.toFloat()

        drawCanvas.drawRect(startXFirst, 0f, startXFirst + lineWidth, height, clearPaint)
        drawCanvas.drawRect(startXSecond, 0f, startXSecond + lineWidth, height, clearPaint)

        drawCanvas.drawRect(
            first * lineWidth,
            DataUtils.map(
                linesData.numbers[first],
                linesData.numberMin,
                linesData.numberMax,
                minLineHeight,
                maxLineHeight
            ),
            (first + 1) * (lineWidth),
            height,
            linePaint
        )
        drawCanvas.drawRect(
            second * lineWidth,
            DataUtils.map(
                linesData.numbers[second],
                linesData.numberMin,
                linesData.numberMax,
                minLineHeight,
                maxLineHeight
            ),
            (second + 1) * (lineWidth),
            height,
            linePaint
        )

        invalidate()
    }
}

private fun <T> MutableList<T>.swap(from: Int, to: Int) {
    val tmp = this[from]
    this[from] = this[to]
    this[to] = tmp
}
