package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortLogsContract {

    interface ISortLogModel {
        val text: String
    }

    interface ViewContract : IBaseView {
        fun initLogs(models: List<ISortLogModel>)
        fun addNewLog(model: ISortLogModel)

        fun goBack()
    }

    interface PresenterContract : IBasePresenter<ViewContract> {
        fun addLog(logModel: ISortLogModel)

        fun onSortingClicked()
    }

    interface SortLogger {
        fun addLog(logModel: ISortLogModel)
    }
}