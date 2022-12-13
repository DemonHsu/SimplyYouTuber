package com.example.movieapp.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
import com.example.movieapp.home.databinding.FragmentHomeBinding
import com.example.movieapp.home.redux.HomeState
import com.example.movieapp.home.redux.HomeState.GetHomeCatalogsStarted
import com.example.movieapp.home.redux.HomeState.HomeCatalogsError
import com.example.movieapp.home.redux.HomeState.HomeCatalogsLoaded
import com.example.movieapp.home.redux.HomeState.HomeCatalogsNextPageLoaded
import com.example.movieapp.home.redux.HomeState.Idle
import com.example.movieapp.home.redux.HomeViewModel
import com.example.movieapp.presentation.base.BaseFragment
import com.example.movieapp.presentation.common.PlayerHelper
import com.example.movieapp.presentation.common.UiHelper.hideViews
import com.example.movieapp.presentation.common.UiHelper.showViews
import com.example.movieapp.presentation.model.MovieUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by viewModels()
    lateinit var moviesAdapter: MoviesAdapter

    override fun contentSetup(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.start()
        observeViewState()

    }

    private fun observeViewState() {
        lifecycleScope.launchWhenResumed {
            viewModel.viewState?.collect { viewState ->
                renderView(viewState)
            }
        }
    }

    private fun renderView(viewState: HomeState) {
        when (viewState) {
            is Idle -> return
            is GetHomeCatalogsStarted -> showLoading()
            is HomeCatalogsLoaded -> showMovieCatalogs(viewState)
            is HomeCatalogsError -> showErrorMessage(viewState.message)
            is HomeCatalogsNextPageLoaded -> updateMovieCatalogs(viewState)
        }
    }

    private fun showLoading() {
        binding.apply {
            hideViews(errorContent)
            showViews(homeProgress)
        }
    }

    private fun hideLoading() {
        binding.apply {
            showViews(homeContent)
            hideViews(homeProgress, errorContent)
        }
    }

    private fun displayErrorContent() {
        binding.apply {
            hideViews(homeProgress, homeContent)
            showViews(errorContent)
        }
    }

    private fun showErrorMessage(message: String?) {
        binding.apply {
            errorMessage.text = message
            errorRetryButton.setOnClickListener {
                viewModel.retry()
            }
        }
        displayErrorContent()
    }

    private fun showMovieCatalogs(viewState: HomeCatalogsLoaded) {
        val (popularMovies) = viewState
        setupMovieList(binding.popularList, popularMovies)
        hideLoading()
    }

    private fun updateMovieCatalogs(viewState: HomeCatalogsNextPageLoaded) {
        val (popularMovies) = viewState
        moviesAdapter.submitList(popularMovies)
        hideLoading()
    }

    private fun setupMovieList(list: RecyclerView, movies: List<MovieUI>) {
        moviesAdapter = MoviesAdapter(::onMovieClicked)

        list.apply {
            moviesAdapter.stateRestorationPolicy = PREVENT_WHEN_EMPTY
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
        }

        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if ((list.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition() == (moviesAdapter.itemCount - 1)) {
                        viewModel.nextPage()
                }
            }
        })

        moviesAdapter.submitList(movies)
    }

    private fun onMovieClicked(movie: MovieUI) {
        val intent = Intent(requireActivity(), PlayerActivity::class.java)
        intent.putExtra(PlayerHelper.VIDEO_KEY, movie)
        startActivity(intent)
    }

    override fun onDestroyView() {
        binding.apply {
            popularList.adapter = null
        }

        super.onDestroyView()
    }
}
