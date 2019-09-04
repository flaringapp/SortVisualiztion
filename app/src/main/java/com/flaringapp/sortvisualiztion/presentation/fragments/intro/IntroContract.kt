package com.flaringapp.sortvisualiztion.presentation.fragments.intro

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView


interface IntroContract {

    interface ViewContract: IBaseView {

    }

    interface PresenterContract: IBasePresenter<ViewContract> {

    }
}