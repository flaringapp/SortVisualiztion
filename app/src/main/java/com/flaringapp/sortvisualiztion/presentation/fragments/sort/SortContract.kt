package com.flaringapp.sortvisualiztion.presentation.fragments.sort

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortContract {

    interface ViewContract : IBaseView {
        fun updateViewSortArray(array: IntArray)
        fun onSortCompleted()
    }

    interface PresenterContract : IBasePresenter<ViewContract> {
        fun sort(viewElementsCount: Int)
    }

}