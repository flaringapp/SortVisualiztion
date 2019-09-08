package com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethodsContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import org.koin.androidx.scope.currentScope

class SortMethodsFragment: BaseFragment<SortMethodsContract.PresenterContract>(),
    SortMethodsContract.ViewContract {

    override val presenter: SortMethodsContract.PresenterContract by currentScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort_type, container, false)
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