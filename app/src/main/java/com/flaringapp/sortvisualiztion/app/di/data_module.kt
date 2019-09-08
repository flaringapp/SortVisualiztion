package com.flaringapp.sortvisualiztion.app.di

import com.flaringapp.sortvisualiztion.data.managers.bubble_sort.SortManager
import com.flaringapp.sortvisualiztion.data.managers.bubble_sort.SortManagerImpl
import org.koin.dsl.module

val data_module = module {

    single { SortManagerImpl() as SortManager }

}