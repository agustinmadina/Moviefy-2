package com.agustin.sample.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.agustin.sample.R
import com.agustin.sample.databinding.FragmentMainBinding
import com.agustin.sample.extensions.getIOErrorMessage
import com.agustin.sample.extensions.showToast
import com.agustin.sample.extensions.viewBinding
import com.agustin.sample.viewmodels.MainViewModel
import com.agustin.sample.viewmodels.MovieState
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Agustin Madina on 23/04/2021.
 */
class MainFragment : Fragment(R.layout.fragment_main) {
    private val dateViewModel: MainViewModel by viewModel()
    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        dateViewModel.getTopRatedMovies()
    }

    private fun setupObservers() {
        dateViewModel.moviesState.observe(viewLifecycleOwner) { state ->
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

        dateViewModel.movies.observe(viewLifecycleOwner) { dates ->
            binding.sampleText.text = dates[0].title
        }
    }
}