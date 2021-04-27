package com.agustin.sample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.agustin.sample.R
import com.agustin.sample.adapters.MoviesAdapter
import com.agustin.sample.databinding.FragmentMainBinding
import com.agustin.sample.extensions.getIOErrorMessage
import com.agustin.sample.extensions.showToast
import com.agustin.sample.extensions.viewBinding
import com.agustin.sample.network.models.Movie
import com.agustin.sample.viewmodels.MainViewModel
import com.agustin.sample.viewmodels.MovieState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Agustin Madina on 23/04/2021.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
    private val movieViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        movieViewModel.getTopRatedMovies()

        binding.searchIcon.setOnClickListener {
            movieViewModel.searchMovie(binding.searchEditText.text.toString())
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.moviesRecyclerView.layoutManager = layoutManager
        moviesAdapter = MoviesAdapter(
            openMovieDetailsFn = { movie ->
                openMovie(movie)
            }
        )
        binding.moviesRecyclerView.adapter = moviesAdapter
    }

    private fun openMovie(movie: Movie) {
        val directions = MainFragmentDirections.showMovieDetail(movie)
        findNavController().navigate(directions)
    }

    private fun setupObservers() {
        movieViewModel.moviesState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is MovieState.Loading -> binding.loadingProgressBar.show()
                is MovieState.Success -> binding.loadingProgressBar.hide()
                is MovieState.Error -> {
                    binding.loadingProgressBar.hide()
                    val message = state.exception.getIOErrorMessage(requireContext())
                    requireContext().showToast(message)
                }
            }
        }

        movieViewModel.movies.observe(viewLifecycleOwner) { movies ->
           moviesAdapter.submitList(movies)
            moviesAdapter.notifyDataSetChanged()
        }
    }
}