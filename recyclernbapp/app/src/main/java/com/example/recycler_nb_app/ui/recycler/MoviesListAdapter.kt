package com.example.recycler_nb_app.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.recycler_nb_app.databinding.ItemMovieBinding
import com.example.recycler_nb_app.databinding.ItemMovieCatalogBinding
import com.example.recycler_nb_app.ui.movies.Movie
import com.example.recycler_nb_app.ui.movies.MovieCatalog

class MoviesListAdapter(


): ListAdapter<Movie, BaseListViewHolder<*>>(DiffUtilCallback) {

    private object DiffUtilCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListViewHolder<*> {
        val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BindViewHolderList(itemBinding)
    }

    override fun onBindViewHolder(holder: BaseListViewHolder<*>, position: Int) {
        when (holder) {
            is BindViewHolderList -> holder.bind(getItem(position), position)
        }
    }

    inner class BindViewHolderList(private val binding: ItemMovieBinding) : BaseListViewHolder<Movie>(binding.root) {

        override fun bind(item: Movie, position: Int) = with(binding) {

            movieImg.setImageResource(item.imageRes)

            movieImg.setOnClickListener {
                onMovieClickListener?.let { click ->
                    click(item)
                }
            }
        }
    }

    private var onMovieClickListener: ((Movie) -> Unit)? = null

    fun setMovieClickListener(listener: (Movie) -> Unit) {
        onMovieClickListener = listener
    }
}