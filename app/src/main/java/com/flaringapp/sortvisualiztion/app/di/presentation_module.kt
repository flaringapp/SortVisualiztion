package com.flaringapp.sortvisualiztion.app.di

import com.flaringapp.sortvisualiztion.presentation.fragments.create_array.CreateArrayContract
import com.flaringapp.sortvisualiztion.presentation.fragments.create_array.impl.CreateArrayFragment
import com.flaringapp.sortvisualiztion.presentation.fragments.create_array.impl.CreateArrayPresenter
import com.flaringapp.sortvisualiztion.presentation.fragments.intro.IntroContract
import com.flaringapp.sortvisualiztion.presentation.fragments.intro.impl.IntroFragment
import com.flaringapp.sortvisualiztion.presentation.fragments.intro.impl.IntroPresenter
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl.SortFragment
import com.flaringapp.sortvisualiztion.presentation.fragments.sort.impl.SortPresenter
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_type.SortTypeContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_type.impl.SortTypeFragment
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_type.impl.SortTypePresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentation_module = module {

    scope(named<IntroFragment>()) {
        scoped { IntroPresenter() as IntroContract.PresenterContract }
    }

    scope(named<CreateArrayFragment>()) {
        scoped { CreateArrayPresenter() as CreateArrayContract.PresenterContract }
    }

    scope(named<SortTypeFragment>()) {
        scoped { SortTypePresenter() as SortTypeContract.PresenterContract }
    }

    scope(named<SortFragment>()) {
        scoped { SortPresenter() as SortContract.PresenterContract }
    }

}