package com.flaringapp.sortvisualiztion.presentation.fragments.sort.models

import com.flaringapp.sortvisualiztion.presentation.fragments.sort.SortContract
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_methods.SortMethod
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SortData(
    override val array: IntArray,
    override val method: SortMethod
) : SortContract.ISortData