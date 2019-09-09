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
import org.koin.androidx.scope.currentScope

class SortLogsFragment : BaseFragment<SortLogsContract.PresenterContract>(),
    SortLogsContract.ViewContract, SortLogsContract.SortLogger {

    override val presenter: SortLogsContract.PresenterContract by currentScope.inject()

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

    override fun initLogs(logs: List<String>) {
        (logsRecycler.adapter as SortLogsAdapter).setModels(logs)
    }

    override fun addNewLog(log: String) {
        logsRecycler?.apply {
            (adapter as SortLogsAdapter).addModel(log)
            val lastVisiblePosition = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            val itemsCount = adapter!!.itemCount
            if (lastVisiblePosition >= itemsCount - 1 - SCROLL_ITEM_TOLLRENCE) {
                smoothScrollToPosition(itemsCount - 1)
            }
        }
    }

    override fun goBack() {
        activity?.onBackPressed()
    }

    override fun addLog(log: String) {
        presenter.addLog(log)
    }

    private fun initViews() {
        sortingButton.setOnClickListener { presenter.onSortingClicked() }

        logsRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false).apply {
                stackFromEnd = true
            }
            adapter = SortLogsAdapter()
        }
    }

    companion object {
        private const val SCROLL_ITEM_TOLLRENCE = 1

        fun newInstance(): SortLogsFragment {
            return SortLogsFragment()
        }
    }
}