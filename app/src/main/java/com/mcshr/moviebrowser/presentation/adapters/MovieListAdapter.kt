package com.mcshr.moviebrowser.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.mcshr.moviebrowser.R
import com.mcshr.moviebrowser.databinding.ItemMovieBinding
import com.mcshr.moviebrowser.domain.entities.Movie

class MovieListAdapter(
    private val onItemClick: (Movie) -> Unit,
    private val onFavButtonClick: (Movie) -> Unit,
    private val adapterType: AdapterType = AdapterType.ALL
) : androidx.recyclerview.widget.ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        val movie = getItem(position)
        with(holder) {
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
            binding.btnFavorite.setOnClickListener {
                onFavButtonClick(movie)
            }
            when (adapterType) {
                AdapterType.ALL -> {
                    val icon = if (movie.isFavorite) {
                        R.drawable.ic_star_filled
                    } else {
                        R.drawable.ic_star
                    }
                    binding.btnFavorite.setImageResource(icon)
                }

                AdapterType.FAVORITES -> {
                    binding.btnFavorite.setImageResource(R.drawable.ic_delete)
                }
            }

            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview
            binding.ivPoster.load(movie.posterUrl)
            Log.d("POSTERURL", movie.posterUrl)
        }
    }

}

enum class AdapterType { ALL, FAVORITES }
class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
class MovieDiffCallback() : androidx.recyclerview.widget.DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Movie,
        newItem: Movie
    ): Boolean {
        return oldItem.id == newItem.id
    }
}