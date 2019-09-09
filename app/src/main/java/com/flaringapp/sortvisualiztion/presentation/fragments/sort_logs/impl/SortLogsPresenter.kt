package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.impl

import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.SortLogsContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter

class SortLogsPresenter: BasePresenter<SortLogsContract.ViewContract>(),
    SortLogsContract.PresenterContract {

    override fun addLog(logModel: SortLogsContract.ISortLogModel) {
        view?.addNewLog(logModel)
    }

    override fun onSortingClicked() {
        view?.goBack()
    }

}