package com.flaringapp.sortvisualiztion.presentation.fragments.create_array.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.activities.main.MainContract
import com.flaringapp.sortvisualiztion.presentation.fragments.create_array.CreateArrayContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import kotlinx.android.synthetic.main.fragment_create_array.*
import org.koin.androidx.scope.currentScope

class CreateArrayFragment: BaseFragment<CreateArrayContract.PresenterContract>(), CreateArrayContract.ViewContract {

    override val presenter: CreateArrayContract.PresenterContract by currentScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_array, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onInitPresenter() {
        presenter.view = this
        presenter.init(activity as MainContract.AppNavigation)
    }

    override fun updateArrayText(text: String) {
        arrayText.text = text
    }

    private fun initViews() {
        buttonRandom.setOnClickListener { presenter.onRandomClicked() }
        buttonContinue.setOnClickListener { presenter.onContinueClicked() }
    }

    companion object {
        fun newInstance() = CreateArrayFragment()
    }
}