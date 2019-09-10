package com.flaringapp.sortvisualiztion.data.managers.sort

import com.flaringapp.sortvisualiztion.utils.onComputationThread
import com.flaringapp.sortvisualiztion.utils.swap
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

class SortManagerImpl : SortManager {

    override fun bubbleSortIncrease(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
                var iteration = 0L

                for (pass in 0 until (numbers.size - 1)) {
                    for (index in 0 until (numbers.size - 1)) {
                        if (numbers[index] > numbers[index + 1]) {
                            numbers.swap(index, index + 1)
                        }

                        if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                        iteration++
                    }
                }
                emitter.onNext(numbers)
                emitter.onComplete()
            },
            BackpressureStrategy.LATEST
        )
            .onComputationThread()
    }

    override fun bubbleSortFlaggedIncrease(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
                var iteration = 0L
                var swapped: Boolean

                for (pass in 0 until (numbers.size - 1)) {
                    swapped = false

                    for (index in 0 until (numbers.size - 1)) {
                        if (numbers[index] > numbers[index + 1]) {
                            numbers.swap(index, index + 1)
                            swapped = true
                        }

                        if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                        iteration++
                    }

                    if (!swapped) break
                }
                emitter.onNext(numbers)
                emitter.onComplete()
            },
            BackpressureStrategy.LATEST
        )
            .onComputationThread()
    }

    override fun bubbleSortDecrease(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
                var iteration = 0L

                for (pass in 0 until (numbers.size - 1)) {
                    for (index in 0 until (numbers.size - 1)) {
                        if (numbers[index] < numbers[index + 1]) {
                            numbers.swap(index, index + 1)
                        }

                        if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                        iteration++
                    }
                }
                emitter.onNext(numbers)
                emitter.onComplete()
            },
            BackpressureStrategy.LATEST
        )
            .onComputationThread()
    }

    override fun bubbleSortFlaggedDecrease(
        numbers: IntArray,
        updateFrequency: Int
    ): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
                var iteration = 0L
                var swapped: Boolean

                for (pass in 0 until (numbers.size - 1)) {
                    swapped = false

                    for (index in 0 until (numbers.size - 1)) {
                        if (numbers[index] < numbers[index + 1]) {
                            numbers.swap(index, index + 1)
                            swapped = true
                        }

                        if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                        iteration++
                    }

                    if (!swapped) break
                }
                emitter.onNext(numbers)
                emitter.onComplete()
            },
            BackpressureStrategy.LATEST
        )
            .onComputationThread()
    }

    override fun selectionSortIncrease(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
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

                    if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                }

                emitter.onNext(numbers)
                emitter.onComplete()
            },
            BackpressureStrategy.LATEST
        )
            .onComputationThread()
    }

    override fun selectionSortDecrease(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
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

                    if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                }

                emitter.onNext(numbers)
                emitter.onComplete()
            },
            BackpressureStrategy.LATEST
        )
            .onComputationThread()
    }
}