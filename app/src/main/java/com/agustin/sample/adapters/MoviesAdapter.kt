package com.agustin.sample.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.agustin.sample.BuildConfig.IMAGE_BASE_URL
import com.agustin.sample.databinding.ListItemMovieBinding
import com.agustin.sample.extensions.viewBinding
import com.agustin.sample.network.models.Movie

typealias MovieDetailsFn = (Movie) -> Unit

class MoviesAdapter(
    private val openMovieDetailsFn: MovieDetailsFn? = null,
) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(MessageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.viewBinding(ListItemMovieBinding::inflate), openMovieDetailsFn)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemMovieBinding,
        private val viewTutorialDetailsFn: MovieDetailsFn?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.movieTitleText.text = item.title
            binding.movieImage.setImageDrawable(null)
            val imagePath = item.posterPath
            binding.movieImage.load("${IMAGE_BASE_URL}$imagePath")
            binding.holder.setOnClickListener {
                viewTutorialDetailsFn?.invoke(item)
            }
        }
    }

    private class MessageDiffCallback : DiffUtil.ItemCallback<Movie>() {

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
            oldItem == newItem
    }
}
