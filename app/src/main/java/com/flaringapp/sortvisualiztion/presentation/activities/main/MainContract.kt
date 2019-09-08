package com.flaringapp.sortvisualiztion.presentation.activities.main

import androidx.fragment.app.FragmentManager
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.activities.main.navigation.Screen
import com.flaringapp.sortvisualiztion.presentation.mvp.IBasePresenter
import com.flaringapp.sortvisualiztion.presentation.mvp.IBaseView

interface MainContract {

    interface ViewContract: IBaseView {
    }

    interface PresenterContract: IBasePresenter<ViewContract> {
        fun init(fragmentManager: FragmentManager)
        fun onNavigationRequested(screen: Screen, data: Any? = null, inAnim: Int, outAnim: Int)
    }

    interface AppNavigation {
        fun openScreen(
            screen: Screen,
            data: Any? = null,
            inAnim: Int = R.anim.fragment_appear_from_right,
            outAnim: Int = R.anim.fragment_disappear_to_left
        )
    }

}