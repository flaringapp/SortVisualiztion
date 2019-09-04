package com.flaringapp.sortvisualiztion.presentation.fragments.create_array

import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface CreateArrayContract {

    interface ViewContract : IBaseView {

    }

    interface PresenterContract : IBasePresenter<ViewContract> {

    }

}