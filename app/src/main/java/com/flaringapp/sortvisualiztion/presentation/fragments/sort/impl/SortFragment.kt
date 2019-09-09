package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract.Companion.SORT_DATA_KEY
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.SortLogsContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.impl.SortLogsFragment
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_sort.*
import org.koin.androidx.scope.currentScope

class SortFragment : BaseFragment<SortContract.PresenterContract>(), SortContract.ViewContract {

    override val presenter: SortContract.PresenterContract by currentScope.inject()

    private var sortLogger: SortLogsContract.SortLogger? = null

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

    override fun onDestroyView() {
        sortLogger = null
        super.onDestroyView()
    }

    override fun onInitPresenter() {
        presenter.view = this
    }

    override fun updateCaptionText(textRes: Int) {
        header?.setText(textRes)
    }

    override fun updateCaptionText(text: String) {
        header?.text = text
    }

    override fun setArraySizeText(text: String) {
        arraySizeText.text = text
    }

    override fun setSortMethodText(text: String) {
        sortMethodText.text = text
    }

    override fun updateViewSortArray(array: IntArray) {
        sortView.invalidateNumbers(array)
    }

    override fun initLogsFragment() {
        SortLogsFragment.newInstance().let {
            sortLogger = it
            childFragmentManager.commit {
                addToBackStack(null)
                add(R.id.logsContainer, it)
                hide(it)
            }
        }
    }

    override fun showLogsFragment() {
        childFragmentManager.commit {
            setCustomAnimations(
                R.anim.fragment_appear_from_right,
                R.anim.fragment_disappear_to_left,
                R.anim.fragment_appear_from_left,
                R.anim.fragment_disappear_to_right
            )
            show(childFragmentManager.findFragmentById(R.id.logsContainer)!!)
        }
    }

    override fun addLog(log: String) {
        sortLogger?.addLog(log)
    }

    private fun initViews() {
        sortLogsButton.setOnClickListener { presenter.onLogsClicked() }
        sortView.clear()
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