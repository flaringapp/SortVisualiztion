package com.flaringapp.sortvisualiztion.data.managers.bubble_sort

import io.reactivex.Flowable

interface SortManager {

    companion object {
        private const val DEFAULT_UPDATE_FREQUENCY = 1
    }

    fun bubbleSort(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY
    ): Flowable<IntArray>

    fun bubbleSortFlagged(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY
    ): Flowable<IntArray>
}