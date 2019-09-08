package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import android.os.Bundle
import com.flaringapp.sortvisualiztion.data.managers.sort.SortManager
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract.Companion.SORT_DATA_KEY
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter
import com.flaringapp.sortvisualiztion.utils.RxUtils
import com.flaringapp.sortvisualiztion.utils.observeOnUI
import com.flaringapp.sortvisualiztion.utils.onApiThread
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class SortPresenter(
    private val sortManager: SortManager
) : BasePresenter<SortContract.ViewContract>(), SortContract.PresenterContract {

    companion object {
        private const val VIEW_UPDATE_DELAY = 50
        private const val VIEW_ELEMENTS_COUNT = 100
    }

    private lateinit var sortData: SortContract.ISortData

    private var sortDisposables = CompositeDisposable()
    private var viewUpdateDisposables = CompositeDisposable()

    override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onCreate(arguments, savedInstanceState)
        sortData = arguments!!.getParcelable(SORT_DATA_KEY)!!
    }

    override fun onStart() {
        super.onStart()
        startSorting()
    }

    override fun release() {
        sortDisposables.dispose()
        viewUpdateDisposables.dispose()
        super.release()
    }

    private fun startSorting() {
        val updateViewSubject = PublishSubject.create<IntArray>()

        viewUpdateDisposables += RxUtils.createDelayedFlowable(updateViewSubject, VIEW_UPDATE_DELAY)
            .onApiThread()
            .observeOnUI()
            .doFinally { view?.onSortCompleted() }
            .subscribe {
                view?.updateViewSortArray(it)
            }

        sortDisposables += sortData.method.toAction(sortData.array)
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