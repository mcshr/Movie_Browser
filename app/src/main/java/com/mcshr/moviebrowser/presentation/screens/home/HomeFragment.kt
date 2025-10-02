package com.mcshr.moviebrowser.presentation.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mcshr.moviebrowser.databinding.FragmentHomeBinding
import com.mcshr.moviebrowser.domain.wrappers.NetworkResult
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

        moviesAdapter = MovieListAdapter(
            {
                //navigate
            },
            { movie ->
                viewModel.toggleFavorite(movie)
            }
        )
        viewModel.loadMovies()
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.viewLoading.visibility = View.GONE
                    binding.viewError.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    binding.viewLoading.visibility = View.GONE
                    binding.viewError.visibility = View.VISIBLE
                    binding.tvError.text = it.error.getErrorMessage(requireContext())
                }

                is NetworkResult.Loading -> {
                    binding.viewLoading.visibility = View.VISIBLE
                    binding.viewError.visibility = View.GONE
                }
            }
        }
        binding.rvMovieList.adapter = moviesAdapter
        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.submitList(it)
        }

        binding.btnReload.setOnClickListener {
            viewModel.loadMovies()
        }

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}