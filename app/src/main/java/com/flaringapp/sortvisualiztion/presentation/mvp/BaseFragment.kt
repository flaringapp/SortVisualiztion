package com.flaringapp.sortvisualiztion.presentation.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

abstract class BaseFragment<T : IBasePresenter<*>> : Fragment(), IBaseView {

    abstract val presenter: T

    override val viewContext: Context? get() = context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onCreate(arguments, savedInstanceState)
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        presenter.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onInitPresenter()
        super.onViewCreated(view, savedInstanceState)
        presenter.onStart()
    }

    @CallSuper
    override fun onDestroyView() {
        presenter.release()
        super.onDestroyView()
    }
}