package com.flaringapp.sortvisualiztion.presentation.mvp

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<T : IBasePresenter<*>> : DialogFragment(), IBaseView {

    abstract var presenter: T?

    override val viewContext: Context? get() = context

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter?.onCreate(arguments, savedInstanceState)
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        presenter?.saveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        onInitPresenter()
        super.onViewCreated(view, savedInstanceState)
        presenter?.onStart()
    }

    @CallSuper
    override fun onDestroyView() {
        presenter?.release()
        presenter = null
        super.onDestroyView()
    }

    abstract fun onInitPresenter()
}