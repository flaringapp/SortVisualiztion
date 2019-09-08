package com.flaringapp.sortvisualiztion.presentation.activities.main.impl

import android.os.Bundle
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.presentation.activities.main.MainContract
import com.flaringapp.sortvisualiztion.presentation.activities.main.navigation.Screen
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseActivity
import org.koin.androidx.scope.currentScope

class MainActivity : BaseActivity<MainContract.PresenterContract>(), MainContract.ViewContract,
    MainContract.AppNavigation {

    override val presenter: MainContract.PresenterContract by currentScope.inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onInitPresenter()
        presenter.onStart()

        openScreen(Screen.INTRO, inAnim = 0)
    }

    override fun onInitPresenter() {
        presenter.view = this
        presenter.init(supportFragmentManager)
    }

    override fun openScreen(screen: Screen, data: Any?, inAnim: Int, outAnim: Int) {
        presenter.onNavigationRequested(screen, data, inAnim, outAnim)
    }
}
