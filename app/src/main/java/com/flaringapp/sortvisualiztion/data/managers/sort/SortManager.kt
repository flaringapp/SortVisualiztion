package com.flaringapp.sortvisualiztion.data.managers.sort

import io.reactivex.Flowable

interface SortManager {

    companion object {
        private const val DEFAULT_UPDATE_FREQUENCY = 1
    }

    fun bubbleSortIncrease(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY,
        accurateLogging: Boolean = false
    ): Flowable<IntArray>

    fun bubbleSortFlaggedIncrease(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY,
        accurateLogging: Boolean = false
    ): Flowable<IntArray>

    fun bubbleSortDecrease(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY,
        accurateLogging: Boolean = false
    ): Flowable<IntArray>

    fun bubbleSortFlaggedDecrease(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY,
        accurateLogging: Boolean = false
    ): Flowable<IntArray>

    fun selectionSortIncrease(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY,
        accurateLogging: Boolean = false
    ): Flowable<IntArray>

    fun selectionSortDecrease(
        numbers: IntArray,
        updateFrequency: Int = DEFAULT_UPDATE_FREQUENCY,
        accurateLogging: Boolean = false
    ): Flowable<IntArray>
}