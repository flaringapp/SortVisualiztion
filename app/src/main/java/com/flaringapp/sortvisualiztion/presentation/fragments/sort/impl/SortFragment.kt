package com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import com.flaringapp.sortvisualiztion.presentation.views.sort_view.models.LinesData
import kotlinx.android.synthetic.main.fragment_sort.*
import kotlinx.coroutines.*
import org.koin.androidx.scope.currentScope

class SortFragment: BaseFragment<SortContract.PresenterContract>(), SortContract.ViewContract {

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

    private fun initViews() {
        sortView.fillColor = Color.BLACK
        sortView.setLineColor(Color.RED)
        sortView.setData(
            LinesData(
                mutableListOf(
                    1f,
                    2f,
                    3f,
                    4f,
                    5f,
                    6f,
                    7f,
                    8f,
                    9f,
                    10f,
                    11f,
                    12f,
                    13f,
                    14f,
                    15f,
                    16f,
                    17f,
                    18f,
                    19f,
                    20f
                )
            )
        )

        sortView.postDelayed({
            GlobalScope.launch {
                for (i in 0 until 10) {
                    delay(50)
                    withContext(Dispatchers.Main) { sortView.notifyMove(i, 0) }
                }
            }
        }, 1000)
    }

    companion object {
        fun newInstance() = SortFragment()
    }
}