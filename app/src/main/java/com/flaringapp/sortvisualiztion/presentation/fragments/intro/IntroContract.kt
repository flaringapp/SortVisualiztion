package com.flaringapp.sortvisualiztion.presentation.fragments.intro

import com.flaringapp.sortvisualiztion.presentation.activities.main.MainContract
import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView


interface IntroContract {

    interface ViewContract: IBaseView {

    }

    interface PresenterContract: IBasePresenter<ViewContract> {
        fun init(appNavigation: MainContract.AppNavigation)

        fun onStartClicked()

    }
}