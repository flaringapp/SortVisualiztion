package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.SortLogsContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import org.koin.android.ext.android.inject

class SortLogsFragment : BaseFragment<SortLogsContract.PresenterContract>(),
    SortLogsContract.ViewContract {

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

    private fun initViews() {

    }
}