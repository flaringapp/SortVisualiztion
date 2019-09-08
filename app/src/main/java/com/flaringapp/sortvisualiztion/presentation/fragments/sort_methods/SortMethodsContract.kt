package com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortMethodsContract {

    interface ViewContract : IBaseView {

    }

    interface PresenterContract : IBasePresenter<ViewContract> {

    }

}