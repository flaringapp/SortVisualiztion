package com.flaringapp.sortvisualiztion.utils

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

object RxUtils {

    fun <T> createDelayedFlowable(delayedSubject: PublishSubject<T>, delay: Int): Flowable<T> {
        var lastUpdateTime = 0L
        return delayedSubject.toFlowable(BackpressureStrategy.LATEST)
            .doOnNext {
                if (System.currentTimeMillis() - lastUpdateTime >= delay) {
                    delayedSubject.onNext(it)
                }
                lastUpdateTime = System.currentTimeMillis()
            }
            .doOnComplete { delayedSubject.onComplete() }
            .doOnError { delayedSubject.onError(it) }
            .onApiThread()
    }

}

fun<T> Observable<T>.onApiThread() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun<T> Observable<T>.observeOnUI() = this
    .observeOn(AndroidSchedulers.mainThread())

fun<T> Flowable<T>.onApiThread() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun <T> Flowable<T>.onComputationThread(): Flowable<T> = this
    .subscribeOn(Schedulers.computation())
    .observeOn(Schedulers.computation())

fun<T> Flowable<T>.observeOnUI() = this
    .observeOn(AndroidSchedulers.mainThread())

fun<T> PublishSubject<T>.onApiThread() = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())

fun<T> PublishSubject<T>.observeOnUI() = this
    .observeOn(AndroidSchedulers.mainThread())