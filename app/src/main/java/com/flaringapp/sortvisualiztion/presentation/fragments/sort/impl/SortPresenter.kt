package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import android.os.Bundle
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.data.managers.sort.SortManager
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract.Companion.SORT_DATA_KEY
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter
import com.flaringapp.sortvisualiztion.utils.*
import io.reactivex.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class SortPresenter(
    private val sortManager: SortManager
) : BasePresenter<SortContract.ViewContract>(), SortContract.PresenterContract {

    companion object {
        private const val VIEW_UPDATE_DELAY = 50L
        private const val VIEW_ELEMENTS_COUNT = 200

        private const val LOG_MAX_ELEMENTS_COUNT = 15
        private const val LOG_MAX_UPDATE_SIZE = 250
        private const val LOG_UPDATE_DELAY = 250L

        private const val TIMER_UPDATE_DELAY = 43L

        private val countDown = arrayOf("3", "2", "1")
    }

    private lateinit var sortData: SortContract.ISortData

    private var currentSortArray: IntArray? = null

    private var formatter: DateFormat? = null

    private var countDownDisposable: Disposable? = null
    private var viewUpdateDisposable: Disposable? = null
    private var addLogsDisposable: Disposable? = null
    private var sortDisposable: Disposable? = null
    private var timerDisposable: Disposable? = null

    override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onCreate(arguments, savedInstanceState)
        sortData = arguments!!.getParcelable(SORT_DATA_KEY)!!
    }

    override fun onStart() {
        super.onStart()

        view?.initLogsFragment()

        formatter = SimpleDateFormat("mm:ss:SSS", view!!.viewContext!!.getCurrentLocale()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        view?.setSortMethodText(sortData.methodName)
        view?.setArraySizeText("${getString(R.string.array_length)}: ${sortData.array.size}")

        startCountDown()
    }

    override fun release() {
        countDownDisposable?.dispose()
        viewUpdateDisposable?.dispose()
        addLogsDisposable?.dispose()
        sortDisposable?.dispose()
        timerDisposable?.dispose()
        formatter = null
        super.release()
    }

    override fun onLogsClicked() {
        view?.showLogsFragment()
    }

    override fun requestHideLogs() {
        view?.hideLogsFragment()
    }

    private fun startCountDown() {
        countDownDisposable =
            Observable.intervalRange(0, countDown.size.toLong() + 1, 0, 1, TimeUnit.SECONDS)
                .map { it.toInt() }
                .onApiThread()
                .observeOnUI()
                .subscribe {
                    if (it < countDown.size) {
                        view?.updateCaptionText(countDown[it])
                    } else {
                        startSorting()
                    }
                }
    }

    private fun startSorting() {
        if (sortData.array.size > LOG_MAX_ELEMENTS_COUNT) view?.addLog(
            view?.viewContext?.getString(
                R.string.log_too_big_display_array,
                LOG_MAX_ELEMENTS_COUNT
            )!!
        )

        if (sortData.array.size > LOG_MAX_UPDATE_SIZE) view?.addLog(
            view?.viewContext?.getString(R.string.log_too_big_update_array, LOG_UPDATE_DELAY)!!
        )



        view?.addLog(sortData.array.viewSubList(LOG_MAX_ELEMENTS_COUNT).format())

        val updateViewSubject = PublishSubject.create<IntArray>()
        val addLogsSubject = PublishSubject.create<String>()

        val startTime = System.currentTimeMillis()

        timerDisposable = Observable.interval(TIMER_UPDATE_DELAY, TimeUnit.MILLISECONDS)
            .map { System.currentTimeMillis() }
            .subscribeOn(Schedulers.newThread())
            .observeOnUI()
            .subscribe {
                view?.updateCaptionText(formatter!!.format(it - startTime))
            }

        viewUpdateDisposable = RxUtils.createDelayedFlowable(updateViewSubject, VIEW_UPDATE_DELAY)
            .onApiThread()
            .observeOnUI()
            .subscribeBy(
                onNext = { view?.updateViewSortArray(it) },
                onComplete = {
                    timerDisposable?.dispose()
                    view?.updateViewSortArray(currentSortArray!!.viewSubList(VIEW_ELEMENTS_COUNT))
                    view?.updateCaptionText(R.string.sort_completed)
                }
            )

        addLogsDisposable = if (sortData.array.size > LOG_MAX_UPDATE_SIZE) {
            RxUtils.createDelayedFlowable(addLogsSubject, LOG_UPDATE_DELAY)
        } else {
            addLogsSubject.toFlowable(BackpressureStrategy.LATEST)
        }
            .onApiThread()
            .observeOnUI()
            .subscribeBy(
                onNext = {
                    view?.addLog(it)
                },
                onComplete = {
                    view?.addLog(
                        "${getString(R.string.sort_completed_in)!!} ${formatter!!.format(System.currentTimeMillis() - startTime)}"
                    )
                    view?.addLog(
                        "${getString(R.string.final_array)}: ${currentSortArray!!.viewSubList(
                            LOG_MAX_ELEMENTS_COUNT
                        ).format()}"
                    )
                }
            )

        var numbers = sortData.array.toCollection(ArrayList())

        sortDisposable = Single.fromCallable {
            numbers.removeAll { it % 3 == 0 }
        }
            .map { numbers.toIntArray() }
            .map {
                currentSortArray = it
                it.viewSubList(LOG_MAX_ELEMENTS_COUNT).format()
            }
            .doOnSuccess {
                view?.addLog(getString(R.string.removed_numbers_multiple_three)!!)
                view?.addLog(it)
            }
            .onApiThread()
            .observeOnUI()
            .flatMap {
                Single.fromCallable {
                    numbers = numbers.map { it * it }.toCollection(ArrayList())
                }
                    .map { numbers.toIntArray() }
                    .map {
                        currentSortArray = it
                        it.viewSubList(LOG_MAX_ELEMENTS_COUNT).format()
                    }
                    .doOnSuccess {
                        view?.addLog(getString(R.string.all_numbers_squared)!!)
                        view?.addLog(numbers.toIntArray().viewSubList(LOG_MAX_ELEMENTS_COUNT).format())
                        view?.addLog(getString(R.string.sort_started)!!)
                    }
                    .onApiThread()
                    .observeOnUI()
            }.flatMapPublisher {
                sortData.method.toAction(numbers.toIntArray())
                    .map { it.viewSubList(VIEW_ELEMENTS_COUNT) }
                    .doOnNext {
                        currentSortArray = it
                        updateViewSubject.onNext(
                            it.viewSubList(VIEW_ELEMENTS_COUNT)
                        )
                        addLogsSubject.onNext(
                            it.viewSubList(LOG_MAX_ELEMENTS_COUNT).format()
                        )
                    }
                    .doOnError {
                        updateViewSubject.onError(it)
                        addLogsSubject.onError(it)
                    }
                    .doOnComplete {
                        updateViewSubject.onComplete()
                        addLogsSubject.onComplete()
                    }
            }
            .onApiThread()
            .subscribe()
    }

    private fun SortMethod.toAction(numbers: IntArray): Flowable<IntArray> {
        return when (this) {
            SortMethod.BUBBLE -> sortManager.bubbleSortDecrease(numbers)
            SortMethod.BUBBLE_FLAGGED -> sortManager.bubbleSortFlaggedDecrease(numbers)
            SortMethod.SELECTION -> sortManager.selectionSortDecrease(numbers)
        }
    }
}

private fun IntArray.viewSubList(elementsCount: Int): IntArray {
    if (size <= elementsCount) return this

    val step = size / elementsCount

    return IntArray(elementsCount) { i -> this[i * step] }
}