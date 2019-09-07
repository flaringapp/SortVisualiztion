package com.flaringapp.sortvisualiztion.data.managers.bubble_sort

import com.flaringapp.sortvisualiztion.utils.onComputationThread
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class BubbleSortManagerImpl : BubbleSortManager {

    override fun sort(numbers: IntArray, updateFrequency: Int): Flowable<IntArray> {
        return Flowable.create<IntArray>(
            { emitter ->
                var iteration = 0L
                for (pass in 0 until (numbers.size - 1)) {
                    // A single pass of bubble sort
                    for (currentPosition in 0 until (numbers.size - 1)) {
                        // This is a single step
                        if (numbers[currentPosition] > numbers[currentPosition + 1]) {
                            numbers[currentPosition] = numbers[currentPosition + 1]
                                .also { numbers[currentPosition + 1] = numbers[currentPosition] }
                        }

                        if (iteration % updateFrequency == 0L) emitter.onNext(numbers)
                        iteration++
                    }
                }
                emitter.onComplete()
            }, BackpressureStrategy.LATEST
        )
            .onComputationThread()
            .observeOn(Schedulers.io())
    }
}
