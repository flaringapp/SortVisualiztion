package com.flaringapp.sortvisualiztion.presentation.fragments.sort

import android.os.Parcelable
import androidx.annotation.StringRes
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortContract {

    companion object {
        const val SORT_DATA_KEY = "key_sort_data"
    }

    interface ISortData: Parcelable {
        val array: IntArray
        val method: SortMethod
    }

    interface ViewContract : IBaseView {
        fun updateCaptionText(@StringRes textRes: Int)
        fun updateCaptionText(text: String)
        fun updateViewSortArray(array: IntArray)
    }

    interface PresenterContract : IBasePresenter<ViewContract> {
    }

}