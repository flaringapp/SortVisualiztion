package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import com.flaringapp.sortvisualiztion.data.managers.bubble_sort.BubbleSortManager
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter
import com.flaringapp.sortvisualiztion.utils.RxUtils
import com.flaringapp.sortvisualiztion.utils.observeOnUI
import com.flaringapp.sortvisualiztion.utils.onApiThread
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class SortPresenter(
    private val sortManager: BubbleSortManager
) : BasePresenter<SortContract.ViewContract>(), SortContract.PresenterContract {

    companion object {
        private const val VIEW_UPDATE_DELAY = 50
    }

    private var sortDisposables = CompositeDisposable()
    private var viewUpdateDisposables = CompositeDisposable()

    override fun release() {
        sortDisposables.dispose()
        viewUpdateDisposables.dispose()
        super.release()
    }

    override fun sort(viewElementsCount: Int) {
        val updateViewSubject = PublishSubject.create<IntArray>()

        viewUpdateDisposables += RxUtils.createDelayedFlowable(updateViewSubject, VIEW_UPDATE_DELAY)
            .onApiThread()
            .observeOnUI()
            .doFinally { view?.onSortCompleted() }
            .subscribe {
                view?.updateViewSortArray(it)
            }

        sortDisposables += sortManager.sort(randomArray())
            .map { viewSubList(it, viewElementsCount) }
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

    private fun randomArray(): IntArray {
        val size = (1000..2000).random()
        return IntArray(size) { (0 until size).random() }
    }
}