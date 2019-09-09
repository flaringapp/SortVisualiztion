package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.SortLogsContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.adapter.SortLogsAdapter
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_sort_logs.*
import org.koin.android.ext.android.inject

class SortLogsFragment : BaseFragment<SortLogsContract.PresenterContract>(),
    SortLogsContract.ViewContract, SortLogsContract.SortLogger {

    override val presenter: SortLogsContract.PresenterContract by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort_logs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onInitPresenter() {
        presenter.view = this
    }

    override fun initLogs(models: List<SortLogsContract.ISortLogModel>) {
        (logsRecycler.adapter as SortLogsAdapter).setModels(models)
    }

    override fun addNewLog(model: SortLogsContract.ISortLogModel) {
        (logsRecycler.adapter as SortLogsAdapter).addModel(model)
    }

    override fun goBack() {
        activity?.onBackPressed()
    }

    override fun addLog(logModel: SortLogsContract.ISortLogModel) {
        presenter.addLog(logModel)
    }

    private fun initViews() {
        sortingButton.setOnClickListener { presenter.onSortingClicked() }

        logsRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true).apply {
                stackFromEnd = true
            }
            adapter = SortLogsAdapter()
        }
    }
}