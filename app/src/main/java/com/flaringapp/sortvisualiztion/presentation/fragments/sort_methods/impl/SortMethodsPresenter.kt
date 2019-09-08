package com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.impl

import android.os.Bundle
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.activities.main.MainContract
import com.flaringapp.sortvisualiztion.presentation.activities.main.navigation.Screen
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.models.SortData
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethodsContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethodsContract.Companion.DATA_ARRAY_KEY
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.models.SortMethodModel
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter

class SortMethodsPresenter : BasePresenter<SortMethodsContract.ViewContract>(),
    SortMethodsContract.PresenterContract {

    private lateinit var dataArray: IntArray
    private var appNavigation: MainContract.AppNavigation? = null

    private var selectedMethod: SortMethod? = null

    override fun onCreate(arguments: Bundle?, savedInstanceState: Bundle?) {
        super.onCreate(arguments, savedInstanceState)
        dataArray = arguments!!.getIntArray(DATA_ARRAY_KEY)!!
    }

    override fun onStart() {
        super.onStart()
        view?.setModels(models)
    }

    override fun release() {
        appNavigation = null
        super.release()
    }

    override fun init(appNavigation: MainContract.AppNavigation) {
        this.appNavigation = appNavigation
    }

    override fun onModelClicked(methodModel: SortMethodsContract.ISortMethodModel) {
        selectedMethod = methodModel.method
    }

    override fun onSortClicked() {
        if (selectedMethod == null) return
        appNavigation?.openScreen(Screen.SORT, SortData(dataArray, selectedMethod!!))
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