package com.flaringapp.sortvisualiztion.utils

object DataUtils {

    fun map(x: Float, a1: Float, a2: Float, b1: Float, b2: Float): Float {
        return b1 + ((x - a1) * (b2 - b1) / (a2 - a1))
    }

}