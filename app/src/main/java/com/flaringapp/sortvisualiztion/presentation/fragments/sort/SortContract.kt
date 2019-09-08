package com.flaringapp.sortvisualiztion.presentation.fragments.sort

import android.os.Parcelable
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortContract {

    interface ISortData: Parcelable {
        val array: IntArray
        val method: SortMethod
    }

    companion object {
        const val SORT_DATA_KEY = "key_sort_data"
    }

    interface ViewContract : IBaseView {
        fun updateViewSortArray(array: IntArray)
        fun onSortCompleted()
    }

    interface PresenterContract : IBasePresenter<ViewContract> {
        fun sort(viewElementsCount: Int)
    }

}