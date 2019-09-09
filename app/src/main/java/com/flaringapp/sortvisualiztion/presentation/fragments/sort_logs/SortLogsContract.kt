package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface SortLogsContract {

    interface ViewContract : IBaseView {
        fun initLogs(logs: List<String>)
        fun addNewLog(log: String)

        fun goBack()
    }

    interface PresenterContract : IBasePresenter<ViewContract> {
        fun addLog(log: String)

        fun onSortingClicked()
    }

    interface SortLogger {
        fun addLog(log: String)
    }
}