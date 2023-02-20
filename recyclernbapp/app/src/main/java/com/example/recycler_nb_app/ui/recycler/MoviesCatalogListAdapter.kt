package com.example.recycler_nb_app.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recycler_nb_app.databinding.ItemMovieBinding
import com.example.recycler_nb_app.databinding.ItemMovieCatalogBinding
import com.example.recycler_nb_app.ui.movies.Movie
import com.example.recycler_nb_app.ui.movies.MovieCatalog

class MoviesCatalogListAdapter(


): ListAdapter<MovieCatalog, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<MovieCatalog>() {
        override fun areItemsTheSame(oldItem: MovieCatalog, newItem: MovieCatalog): Boolean = oldItem.catalog == newItem.catalog
        override fun areContentsTheSame(oldItem: MovieCatalog, newItem: MovieCatalog): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemMovieCatalogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemMovieCatalogBinding) : BaseListViewHolder<MovieCatalog>(binding.root) {

        override fun bind(item: MovieCatalog, position: Int) = with(binding) {

            catalogName.text = item.catalog

            val movieListAdapter = MoviesListAdapter()

            catalogMovies.apply {
                adapter = movieListAdapter
            }

            movieListAdapter.setMovieClickListener {
                onMovieClickListener?.let { click ->
                    click(it)
                }
            }

            movieListAdapter.submitList(item.movies)
        }
    }

    private var onMovieClickListener: ((Movie) -> Unit)? = null

    fun setMovieClickListener(listener: (Movie) -> Unit) {
        onMovieClickListener = listener
    }
}