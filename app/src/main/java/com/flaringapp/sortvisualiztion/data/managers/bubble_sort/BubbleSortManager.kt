package com.flaringapp.sortvisualiztion.data.managers.bubble_sort

import io.reactivex.Flowable

interface BubbleSortManager {

    companion object {
        private const val DEFAULT_UPDATE_FREQUENCY = 1
    }

    fun sort(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY
    ): Flowable<IntArray>

}