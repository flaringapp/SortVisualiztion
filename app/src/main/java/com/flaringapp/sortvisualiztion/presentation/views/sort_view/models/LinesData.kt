package com.flaringapp.sortvisualiztion.presentation.views.sort_view.models

import androidx.annotation.FloatRange

data class LinesData(
    val numbers: MutableList<Float>,
    @FloatRange(from = 0.0, to = 1.0)
    val minHeight: Float = 0f, // Min line visualised height (% of screen)
    @FloatRange(from = 0.0, to = 1.0)
    val maxHeight: Float = 1f // Max line visualised height (% of screen)
) {
    val numberMax = numbers.max()!!
    val numberMin = numbers.min()!!
}