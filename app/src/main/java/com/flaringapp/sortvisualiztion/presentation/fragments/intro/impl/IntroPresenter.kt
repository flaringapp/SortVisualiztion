package com.flaringapp.sortvisualiztion.presentation.fragments.intro.impl

import com.flaringapp.sortvisualiztion.presentation.activities.main.MainContract
import com.flaringapp.sortvisualiztion.presentation.activities.main.navigation.Screen
import com.flaringapp.sortvisualiztion.presentation.fragments.intro.IntroContract
import com.flaringapp.sortvisualiztion.presentation.mvp.BasePresenter

class IntroPresenter: BasePresenter<IntroContract.ViewContract>(), IntroContract.PresenterContract {

    private var appNavigation: MainContract.AppNavigation? = null

    override fun init(appNavigation: MainContract.AppNavigation) {
        this.appNavigation = appNavigation
    }

    override fun release() {
        appNavigation = null
        super.release()
    }

    override fun onStartClicked() {
        appNavigation?.openScreen(Screen.CREATE_ARRAY)
    }
}