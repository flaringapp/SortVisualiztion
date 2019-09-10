package com.flaringapp.sortvisualiztion.data.managers.sort

import com.flaringapp.sortvisualiztion.utils.onComputationThread
import com.flaringapp.sortvisualiztion.utils.swap
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter

class SortManagerImpl : SortManager {

    override fun bubbleSortIncrease(
        numbers: IntArray,
        updateFrequency: Int,
        accurateLogging: Boolean
    ): Flowable<IntArray> {
        return createFlowable { emitter ->
            var iteration = 0L

            for (pass in 0 until (numbers.size - 1)) {
                for (index in 0 until (numbers.size - 1)) {
                    if (numbers[index] > numbers[index + 1]) {
                        numbers.swap(index, index + 1)
                    }

                    if (iteration % updateFrequency == 0L) emitter.onDataUpdated(numbers, accurateLogging)
                    iteration++
                }
            }
            emitter.onNext(numbers)
            emitter.onComplete()
        }
    }

    override fun bubbleSortFlaggedIncrease(
        numbers: IntArray,
        updateFrequency: Int,
        accurateLogging: Boolean
    ): Flowable<IntArray> {
        return createFlowable { emitter ->
            var iteration = 0L
            var swapped: Boolean

            for (pass in 0 until (numbers.size - 1)) {
                swapped = false

                for (index in 0 until (numbers.size - 1)) {
                    if (numbers[index] > numbers[index + 1]) {
                        numbers.swap(index, index + 1)
                        swapped = true
                    }

                    if (iteration % updateFrequency == 0L) emitter.onDataUpdated(numbers, accurateLogging)
                    iteration++
                }

                if (!swapped) break
            }
            emitter.onNext(numbers)
            emitter.onComplete()
        }
    }

    override fun bubbleSortDecrease(
        numbers: IntArray,
        updateFrequency: Int,
        accurateLogging: Boolean
    ): Flowable<IntArray> {
        return createFlowable { emitter ->
            var iteration = 0L

            for (pass in 0 until (numbers.size - 1)) {
                for (index in 0 until (numbers.size - 1)) {
                    if (numbers[index] < numbers[index + 1]) {
                        numbers.swap(index, index + 1)
                    }

                    if (iteration % updateFrequency == 0L) emitter.onDataUpdated(numbers, accurateLogging)
                    iteration++
                }
            }
            emitter.onNext(numbers)
            emitter.onComplete()
        }
    }

    override fun bubbleSortFlaggedDecrease(
        numbers: IntArray,
        updateFrequency: Int,
        accurateLogging: Boolean
    ): Flowable<IntArray> {
        return createFlowable { emitter ->
            var iteration = 0L
            var swapped: Boolean

            for (pass in 0 until (numbers.size - 1)) {
                swapped = false

                for (index in 0 until (numbers.size - 1)) {
                    if (numbers[index] < numbers[index + 1]) {
                        numbers.swap(index, index + 1)
                        swapped = true
                    }

                    if (iteration % updateFrequency == 0L) emitter.onDataUpdated(numbers, accurateLogging)
                    iteration++
                }

                if (!swapped) break
            }
            emitter.onNext(numbers)
            emitter.onComplete()
        }
    }

    override fun selectionSortIncrease(
        numbers: IntArray,
        updateFrequency: Int,
        accurateLogging: Boolean
    ): Flowable<IntArray> {
        return createFlowable { emitter ->
            var iteration = 0L

            for (i in numbers.size - 1 downTo 0) {
                var max = i
                for (j in 0 until i) {
                    iteration++
                    if (numbers[j] > numbers[max])
                        max = j
                }
                if (i != max) {
                    numbers.swap(i, max)
                }

                if (iteration % updateFrequency == 0L) emitter.onDataUpdated(numbers, accurateLogging)
            }

            emitter.onNext(numbers)
            emitter.onComplete()
        }
    }

    override fun selectionSortDecrease(
        numbers: IntArray,
        updateFrequency: Int,
        accurateLogging: Boolean
    ): Flowable<IntArray> {
        return createFlowable { emitter ->
            var iteration = 0L

            for (i in numbers.size - 1 downTo 0) {
                var min = i
                for (j in 0 until i) {
                    iteration++
                    if (numbers[j] < numbers[min])
                        min = j
                }
                if (i != min) {
                    numbers.swap(i, min)
                }

                if (iteration % updateFrequency == 0L) emitter.onDataUpdated(numbers, accurateLogging)
            }

            emitter.onNext(numbers)
            emitter.onComplete()
        }
    }

    private fun <T> createFlowable(action: (FlowableEmitter<T>) -> Unit): Flowable<T> {
        return Flowable.create<T>({ action(it) }, BackpressureStrategy.LATEST)
            .onComputationThread()
    }

    private fun FlowableEmitter<IntArray>.onDataUpdated(numbers: IntArray, accurateLogging: Boolean) {
        if (accurateLogging) onNext(numbers.clone())
        else onNext(numbers)
    }
}
