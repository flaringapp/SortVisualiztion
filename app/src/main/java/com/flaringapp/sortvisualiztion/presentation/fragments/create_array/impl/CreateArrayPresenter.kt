package com.flaringapp.sortvisualiztion.presentation.fragments.create_array.impl

import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.activities.main.MainContract
import com.flaringapp.sortvisualiztion.presentation.activities.main.navigation.Screen
import com.flaringapp.sortvisualiztion.presentation.fragments.create_array.CreateArrayContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter

class CreateArrayPresenter : BasePresenter<CreateArrayContract.ViewContract>(),
    CreateArrayContract.PresenterContract {

    private var appNavigation: MainContract.AppNavigation? = null

    private var array: ArrayList<Int> = ArrayList()

    override fun release() {
        appNavigation = null
        super.release()
    }

    override fun init(appNavigation: MainContract.AppNavigation) {
        this.appNavigation = appNavigation
    }

    override fun onRandomClicked() {
        array = randomArray()
        view?.updateArrayText(array.format())
    }

    override fun onSortClicked() {
        if (array.size < 2) {
            view?.showWarningToast(R.string.too_small_array)
            return
        }

        appNavigation?.openScreen(Screen.SORT_METHODS, array.toTypedArray())
    }

    private fun randomArray(): ArrayList<Int> {
        val size = (1000..2000).random()
        return IntArray(size) { (0 until size).random() }.toCollection(ArrayList())
    }
}

private fun ArrayList<Int>.format(): String {
    return this.joinToString(
        separator = ", ",
        prefix = "[ ",
        postfix = " ]"
    )
}
