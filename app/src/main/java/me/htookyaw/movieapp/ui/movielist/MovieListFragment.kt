package me.htookyaw.movieapp.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.htookyaw.data.utils.EventObserver
import me.htookyaw.data.utils.MovieListException
import me.htookyaw.movieapp.GlideApp
import me.htookyaw.movieapp.R
import me.htookyaw.movieapp.databinding.FragmentMovieListBinding
import me.htookyaw.movieapp.ui.base.BaseFragment
import me.htookyaw.movieapp.utils.exhaustive
import me.htookyaw.movieapp.utils.observeResult

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieListBinding>(), MovieListAdapter.Listener {

    private val viewModel by viewModels<MovieListViewModel>()
    private val moviesAdapter by lazy(mode = LazyThreadSafetyMode.NONE) {
        MovieListAdapter(GlideApp.with(this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeMovieList()
        observeEvents()
    }

    private fun observeEvents() {
        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver { event ->
                when (event) {
                    is MovieListEvent.NavigateDetail -> navigate(event)
                }.exhaustive
            }
        )
    }

    private fun navigate(event: MovieListEvent.NavigateDetail) {
        findNavController().navigate(
            MovieListFragmentDirections.actionFirstFragmentToSecondFragment(
                event.data.id
            )
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieListBinding {
        return FragmentMovieListBinding.inflate(inflater, container, false)
    }

    override fun onItemClick(data: MovieUI, view: View) {
        viewModel.onItemClick(data)
    }

    override fun onFavoriteToggle(data: MovieUI) {
        viewModel.onFavoriteClicked(data)
    }

    private fun setupUI() {
        with(binding.rvMovies) {
            setHasFixedSize(true)
            adapter = moviesAdapter
            with(recycledViewPool) {
                setMaxRecycledViews(0, 25)
            }
            setItemViewCacheSize(10)
        }
        moviesAdapter.listener = this

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.onRefreshed() }
    }

    private fun observeMovieList() {
        viewModel.movies.observeResult<List<MovieUI>, MovieListException>(
            viewLifecycleOwner,
            onLoad = ::showLoading,
            onSuccess = ::showData,
            onError = ::handleError
        )
        viewModel.refreshing.observeResult(
            viewLifecycleOwner,
            onLoad = ::showLoading,
            onError = ::handleError
        )
    }

    private fun showLoading() {
        binding.progress.isVisible = !binding.swipeRefreshLayout.isRefreshing
    }

    private fun showData(movies: List<MovieUI>) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.progress.isVisible = false
        moviesAdapter.submitList(movies)
    }

    private fun handleError(exception: MovieListException) {
        binding.swipeRefreshLayout.isRefreshing = false
        binding.progress.isVisible = false

        when (exception) {
            is MovieListException.NetworkException -> {
                exception.message?.let { showError(it) }
            }
            is MovieListException.ServerException -> {
                exception.message?.let { showError(it) }
            }
            is MovieListException.InvalidException -> {
                exception.message?.let { showError(it) }
            }
            is MovieListException.UnknownException -> {
                exception.message?.let { showError(it) }
            }
        }.exhaustive
    }

    private fun showError(message: String) {
        notifyWithAction(message, getString(R.string.msg_retry)) {
            viewModel.onRefreshed()
        }
    }
}
