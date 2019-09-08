package com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.impl

import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethodsContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.models.SortMethodModel
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter

class SortMethodsPresenter : BasePresenter<SortMethodsContract.ViewContract>(),
    SortMethodsContract.PresenterContract {

    private var selectedMethod: SortMethod? = null

    override fun onStart() {
        super.onStart()
        view?.setModels(models)
    }

    override fun onModelClicked(methodModel: SortMethodsContract.ISortMethodModel) {
        selectedMethod = methodModel.method
    }
}

val models = listOf(
    SortMethodModel(
        R.string.sort_method_bubble,
        SortMethod.BUBBLE
    ),
    SortMethodModel(
        R.string.sort_method_bubble_flagged,
        SortMethod.BUBBLE_FLAGGED
    ),
    SortMethodModel(
        R.string.sort_method_selection,
        SortMethod.SELECTION
    )
) as List<SortMethodsContract.ISortMethodModel>