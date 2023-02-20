package com.example.recycler_nb_app.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recycler_nb_app.databinding.ItemSeriesCatalogBinding
import com.example.recycler_nb_app.ui.series.Series
import com.example.recycler_nb_app.ui.series.SeriesCatalog

class SeriesCatalogListAdapter(


): ListAdapter<SeriesCatalog, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<SeriesCatalog>() {
        override fun areItemsTheSame(oldItem: SeriesCatalog, newItem: SeriesCatalog): Boolean = oldItem.catalog == newItem.catalog
        override fun areContentsTheSame(oldItem: SeriesCatalog, newItem: SeriesCatalog): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemSeriesCatalogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemSeriesCatalogBinding) : BaseListViewHolder<SeriesCatalog>(binding.root) {

        override fun bind(item: SeriesCatalog, position: Int) = with(binding) {

            catalogSeriesName.text = item.catalog

            val seriesListAdapter = SeriesListAdapter()

            catalogSeries.apply {
                adapter =seriesListAdapter
            }

            seriesListAdapter.setMovieClickListener {
                onMovieClickListener?.let { click ->
                    click(it)
                }
            }

            seriesListAdapter.submitList(item.series)
        }
    }

    private var onMovieClickListener: ((Series) -> Unit)? = null

    fun setMovieClickListener(listener: (Series) -> Unit) {
        onMovieClickListener = listener
    }
}