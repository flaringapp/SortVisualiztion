package com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortMethodsContract {

    interface ISortMethodModel {
        val nameRes: Int
        val method: SortMethod
    }

    interface ViewContract : IBaseView {
        fun setModels(models: List<ISortMethodModel>)

        fun showSort()
        fun openSortScreen(sortMethod: SortMethod)
    }

    interface PresenterContract : IBasePresenter<ViewContract> {
        fun onModelClicked(methodModel: ISortMethodModel)

        fun onSortClicked()
    }

}