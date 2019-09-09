package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import android.os.Bundle
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.data.managers.sort.SortManager
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract.Companion.SORT_DATA_KEY
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter
import com.flaringapp.sortvisualiztion.utils.RxUtils
import com.flaringapp.sortvisualiztion.utils.getCurrentLocale
import com.flaringapp.sortvisualiztion.utils.observeOnUI
import com.flaringapp.sortvisualiztion.utils.onApiThread
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class SortPresenter(
    private val sortManager: SortManager
) : BasePresenter<SortContract.ViewContract>(), SortContract.PresenterContract {

    companion object {
        private const val VIEW_UPDATE_DELAY = 50
        private const val VIEW_ELEMENTS_COUNT = 100

        private const val TIMER_UPDATE_DELAY = 50L

        private val countDown = arrayOf("3", "2", "1")
    }

    private lateinit var sortData: SortContract.ISortData

    private var formatter: DateFormat? = null

    private var countDownDisposable: Disposable? = null
    private var viewUpdateDisposable: Disposable? = null
    private var sortDisposable: Disposable? = null
    private var timerDisposable: Disposable? = null

    override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onCreate(arguments, savedInstanceState)
        sortData = arguments!!.getParcelable(SORT_DATA_KEY)!!
    }

    override fun onStart() {
        super.onStart()
        formatter = SimpleDateFormat("mm:ss:SSS", view!!.viewContext!!.getCurrentLocale()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        startCountDown()
    }

    override fun release() {
        countDownDisposable?.dispose()
        viewUpdateDisposable?.dispose()
        sortDisposable?.dispose()
        timerDisposable?.dispose()
        formatter = null
        super.release()
    }

    private fun startCountDown() {
        countDownDisposable = Observable.intervalRange(0, countDown.size.toLong() + 1, 0, 1, TimeUnit.SECONDS)
            .map { it.toInt() }
            .subscribe {
                if (it < countDown.size) {
                    view?.updateCaptionText(countDown[it])
                } else {
                    startSorting()
                }
            }
    }

    private fun startSorting() {
        val updateViewSubject = PublishSubject.create<IntArray>()

        val startTime = System.currentTimeMillis()

        timerDisposable = Observable.interval(TIMER_UPDATE_DELAY, TimeUnit.MILLISECONDS)
            .map { System.currentTimeMillis() }
            .observeOn(Schedulers.newThread())
            .observeOnUI()
            .subscribe {
                view?.updateCaptionText(formatter!!.format(it - startTime))
            }

        viewUpdateDisposable = RxUtils.createDelayedFlowable(updateViewSubject, VIEW_UPDATE_DELAY)
            .onApiThread()
            .observeOnUI()
            .subscribeBy (
                onNext = { view?.updateViewSortArray(it) },
                onComplete = {
                    timerDisposable?.dispose()
                    view?.updateCaptionText(R.string.sort_completed)
                }
            )

        sortDisposable = sortData.method.toAction(sortData.array)
            .map { viewSubList(it, VIEW_ELEMENTS_COUNT) }
            .doOnNext { updateViewSubject.onNext(it) }
            .doOnError { updateViewSubject.onError(it) }
            .doOnComplete { updateViewSubject.onComplete() }
            .subscribe()
    }

    private fun viewSubList(array: IntArray, viewElementsCount: Int): IntArray {
        if (array.size == viewElementsCount) return array

        val step = array.size / viewElementsCount

        return IntArray(viewElementsCount) { i -> array[i * step] }
    }

    private fun SortMethod.toAction(numbers: IntArray): Flowable<IntArray> {
        return when (this) {
            SortMethod.BUBBLE -> sortManager.bubbleSort(numbers)
            SortMethod.BUBBLE_FLAGGED -> sortManager.bubbleSortFlagged(numbers)
            SortMethod.SELECTION -> sortManager.selectionSort(numbers)
        }
    }
}