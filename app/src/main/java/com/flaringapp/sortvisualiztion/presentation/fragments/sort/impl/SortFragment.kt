package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract.Companion.SORT_DATA_KEY
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_sort.*
import org.koin.androidx.scope.currentScope

class SortFragment : BaseFragment<SortContract.PresenterContract>(), SortContract.ViewContract {

    override val presenter: SortContract.PresenterContract by currentScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onInitPresenter() {
        presenter.view = this
    }

    override fun updateViewSortArray(array: IntArray) {
        sortView.invalidateNumbers(array)
    }

    override fun onSortCompleted() {
        timer.text = "Sort completed"
    }

    private fun initViews() {
    }

    companion object {
        fun newInstance(sortData: SortContract.ISortData): SortFragment {
            return SortFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SORT_DATA_KEY, sortData)
                }
            }
        }
    }
}