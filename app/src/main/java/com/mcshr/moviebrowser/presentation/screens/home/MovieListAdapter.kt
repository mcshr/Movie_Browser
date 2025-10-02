package com.mcshr.moviebrowser.presentation.screens.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import com.mcshr.moviebrowser.R
import com.mcshr.moviebrowser.databinding.ItemMovieBinding
import com.mcshr.moviebrowser.domain.entities.Movie

class MovieListAdapter(
    private val onItemClick: (Movie)->Unit,
    private val onFavButtonClick: (Movie)->Unit
) : ListAdapter<Movie, MovieViewHolder>(MovieDiffCallback()) {
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
        with(holder){
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
            binding.btnFavorite.setOnClickListener {
                onFavButtonClick(movie)
            }
            val icon = if(movie.isFavorite){
                R.drawable.ic_star_filled
            } else {
                R.drawable.ic_star
            }
            binding.btnFavorite.setImageResource(icon)

            binding.tvTitle.text = movie.title
            binding.tvOverview.text = movie.overview
            binding.ivPoster.load(movie.posterUrl)
            Log.d("POSTERURL",movie.posterUrl)
        }
    }

}

class MovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)
class MovieDiffCallback() : ItemCallback<Movie>() {
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