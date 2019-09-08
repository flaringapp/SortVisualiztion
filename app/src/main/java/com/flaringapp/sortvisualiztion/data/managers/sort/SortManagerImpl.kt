package com.flaringapp.sortvisualiztion.data.managers.sort

import com.flaringapp.sortvisualiztion.utils.onComputationThread
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class SortManagerImpl : SortManager {

    override fun bubbleSort(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
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
            .observeOn(Schedulers.io())
    }

    override fun bubbleSortFlagged(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
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
            .observeOn(Schedulers.io())
    }
}

private inline fun IntArray.swap(from: Int, to: Int) {
    this[from] = this[to].also { this[to] = this[from] }
}