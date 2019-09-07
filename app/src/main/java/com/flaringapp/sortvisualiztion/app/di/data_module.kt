package com.flaringapp.sortvisualiztion.app.di

import com.flaringapp.sortvisualiztion.data.managers.bubble_sort.BubbleSortManager
import com.flaringapp.sortvisualiztion.data.managers.bubble_sort.BubbleSortManagerImpl
import org.koin.dsl.module

val data_module = module {

    single { BubbleSortManagerImpl() as BubbleSortManager }

}