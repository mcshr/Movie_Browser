package com.mcshr.moviebrowser.presentation.screens.movie_favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.moviebrowser.databinding.FragmentMovieFavoritesBinding
import com.mcshr.moviebrowser.presentation.adapters.AdapterType
import com.mcshr.moviebrowser.presentation.adapters.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFavoritesFragment : Fragment() {

    private var _binding: FragmentMovieFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMovieFavorites binding is null")

    private val viewModel: MovieFavoritesViewModel by viewModels()
    private lateinit var moviesAdapter: MovieListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieFavoritesBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter =
            MovieListAdapter(
                { movie ->
                    val action =
                        MovieFavoritesFragmentDirections.actionMovieFavoritesFragmentToMovieDetailFragment(
                            movieId = movie.id
                        )
                    findNavController().navigate(action)
                },
                { movie ->
                    viewModel.toggleFavoriteMovie(movie)
                },
                AdapterType.FAVORITES
            )
        binding.rvMovieList.adapter = moviesAdapter
        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

}