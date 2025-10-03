package com.mcshr.moviebrowser.presentation.screens.movie_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil3.load
import com.mcshr.moviebrowser.R
import com.mcshr.moviebrowser.databinding.FragmentMovieDetailBinding
import com.mcshr.moviebrowser.domain.wrappers.NetworkResult
import com.mcshr.moviebrowser.presentation.utils.getErrorMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("FragmentMovieDetail binding is null")

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadMovie()
        setOnClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.viewContent.visibility = View.VISIBLE
                    binding.viewLoading.visibility = View.GONE
                    binding.viewError.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    binding.viewContent.visibility = View.GONE
                    binding.viewLoading.visibility = View.GONE
                    binding.viewError.visibility = View.VISIBLE
                    binding.tvError.text = it.error.getErrorMessage(requireContext())
                }

                is NetworkResult.Loading -> {
                    binding.viewContent.visibility = View.GONE
                    binding.viewLoading.visibility = View.VISIBLE
                    binding.viewError.visibility = View.GONE
                }
            }
        }
        viewModel.movie.observe(viewLifecycleOwner) {
            binding.toolbar.title = it.title
            binding.ivPoster.load(it.posterUrl)
            binding.tvTitle.text = it.title
            val icon = if (it.isFavorite) {
                R.drawable.ic_star_filled
            } else {
                R.drawable.ic_star
            }
            binding.btnFavorite.setImageResource(icon)
            binding.tvYear.text = it.releaseDate
            binding.tvDescription.text = it.fullDescription
        }
    }

    private fun setOnClickListeners() {
        binding.btnReload.setOnClickListener {
            viewModel.loadMovie()
        }
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnFavorite.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

}