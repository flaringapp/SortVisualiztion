package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortLogsContract {

    interface ViewContract : IBaseView {

    }

    interface PresenterContract : IBasePresenter<ViewContract> {

    }

}