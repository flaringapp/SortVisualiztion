package com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flaringapp.sortvisualiztion.presentation.fragments.sort_logs.SortLogsContract

class SortLogsAdapter : RecyclerView.Adapter<SortLogsViewHolder>() {

    private val models = ArrayList<SortLogsContract.ISortLogModel>()

    override fun getItemCount() = models.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortLogsViewHolder {
        return SortLogsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SortLogsViewHolder, position: Int) {
        holder.bind(models[position])
    }

    fun setModels(models: List<SortLogsContract.ISortLogModel>) {
        this.models.apply {
            clear()
            addAll(models)
        }
        notifyDataSetChanged()
    }

    fun addModel(model: SortLogsContract.ISortLogModel) {
        models += model
        notifyItemInserted(models.size - 1)
    }
}