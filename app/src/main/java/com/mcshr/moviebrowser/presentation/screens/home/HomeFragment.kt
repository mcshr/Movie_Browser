package com.mcshr.moviebrowser.presentation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mcshr.moviebrowser.R
import com.mcshr.moviebrowser.databinding.FragmentHomeBinding
import com.mcshr.moviebrowser.domain.wrappers.NetworkResult
import com.mcshr.moviebrowser.presentation.adapters.MovieListAdapter
import com.mcshr.moviebrowser.presentation.utils.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentHome binding is null")

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var moviesAdapter: MovieListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMovies()
        setOnClickListeners()
        observeViewModel()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        moviesAdapter =
            MovieListAdapter(
                { movie ->
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToMovieDetailFragment(
                            movieId = movie.id
                        )
                    findNavController().navigate(action)
                },
                { movie ->
                    viewModel.toggleFavorite(movie)
                }
            )
        binding.rvMovieList.adapter = moviesAdapter
    }

    private fun observeViewModel() {
        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.rvMovieList.visibility = View.VISIBLE
                    binding.viewLoading.visibility = View.GONE
                    binding.viewError.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    binding.rvMovieList.visibility = View.GONE
                    binding.viewLoading.visibility = View.GONE
                    binding.viewError.visibility = View.VISIBLE
                    binding.tvError.text = it.error.getErrorMessage(requireContext())
                }

                is NetworkResult.Loading -> {
                    binding.rvMovieList.visibility = View.GONE
                    binding.viewLoading.visibility = View.VISIBLE
                    binding.viewError.visibility = View.GONE
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding.btnReload.setOnClickListener {
            viewModel.loadMovies()
        }
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_favorites -> {
                    val action = HomeFragmentDirections.actionHomeFragmentToMovieFavoritesFragment()
                    findNavController().navigate(action)
                    true
                }

                else -> false
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}