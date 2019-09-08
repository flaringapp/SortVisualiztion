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
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethodsContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.impl.SortMethodsFragment
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.impl.SortMethodsPresenter
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentation_module = module {

    scope(named<IntroFragment>()) {
        scoped { IntroPresenter() as IntroContract.PresenterContract }
    }

    scope(named<CreateArrayFragment>()) {
        scoped { CreateArrayPresenter() as CreateArrayContract.PresenterContract }
    }

    scope(named<SortMethodsFragment>()) {
        scoped { SortMethodsPresenter() as SortMethodsContract.PresenterContract }
    }

    scope(named<SortFragment>()) {
        scoped { SortPresenter(get()) as SortContract.PresenterContract }
    }

}