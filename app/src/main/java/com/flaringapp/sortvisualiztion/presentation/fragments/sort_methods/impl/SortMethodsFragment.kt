package com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.impl

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flaringapp.sortvisualiztion.R
import com.flaringapp.sortvisualiztion.app.constants.Constants.ANIM_DURATION
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethodsContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.adapter.SortMethodsAdapter
import com.flaringapp.sortvisualiztion.presentation.mvp.BaseFragment
import com.flaringapp.sortvisualiztion.utils.view.RecyclerVerticalSpacingDecoration
import com.flaringapp.sortvisualiztion.utils.view.ViewUtils
import com.flaringapp.sortvisualiztion.utils.view.dp
import kotlinx.android.synthetic.main.fragment_sort_type.*
import org.koin.androidx.scope.currentScope

class SortMethodsFragment: BaseFragment<SortMethodsContract.PresenterContract>(),
    SortMethodsContract.ViewContract {

    override val presenter: SortMethodsContract.PresenterContract by currentScope.inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sort_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onInitPresenter() {
        presenter.view = this
    }

    override fun setModels(models: List<SortMethodsContract.ISortMethodModel>) {
        (sortMethodsRecycler.adapter as SortMethodsAdapter).models = models
    }

    override fun showSort() {
        ValueAnimator.ofFloat(buttonSort.translationY, 0f)
            .apply {
                interpolator = DecelerateInterpolator()
                duration = ANIM_DURATION
                addUpdateListener { buttonSort.translationY = it.animatedValue as Float }
                start()
            }
    }

    private fun initViews() {
        sortMethodsRecycler.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = SortMethodsAdapter { presenter.onModelClicked(it) }
            addItemDecoration(
                RecyclerVerticalSpacingDecoration(context.dp(8).toInt())
            )
        }

        buttonSort.doOnLayout { it.translationY = it.measuredHeight.toFloat() }
    }
}