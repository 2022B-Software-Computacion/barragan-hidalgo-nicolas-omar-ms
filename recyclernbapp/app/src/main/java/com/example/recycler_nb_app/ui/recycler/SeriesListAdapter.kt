package com.example.recycler_nb_app.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recycler_nb_app.databinding.ItemSeriesBinding
import com.example.recycler_nb_app.ui.series.Series

class SeriesListAdapter(


): ListAdapter<Series, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<Series>() {
        override fun areItemsTheSame(oldItem: Series, newItem: Series): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Series, newItem: Series): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemSeriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemSeriesBinding) : BaseListViewHolder<Series>(binding.root) {

        override fun bind(item: Series, position: Int) = with(binding) {

            seriesImg.setImageResource(item.imageRes)

            seriesImg.setOnClickListener {
                onMovieClickListener?.let { click ->
                    click(item)
                }
            }
        }
    }

    private var onMovieClickListener: ((Series) -> Unit)? = null

    fun setMovieClickListener(listener: (Series) -> Unit) {
        onMovieClickListener = listener
    }
}