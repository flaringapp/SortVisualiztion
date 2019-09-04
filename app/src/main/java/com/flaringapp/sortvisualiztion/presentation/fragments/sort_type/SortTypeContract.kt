package com.flaringapp.sortvisualiztion.presentation.fragments.sort_type

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortTypeContract {

    interface ViewContract : IBaseView {

    }

    interface PresenterContract : IBasePresenter<ViewContract> {

    }

}