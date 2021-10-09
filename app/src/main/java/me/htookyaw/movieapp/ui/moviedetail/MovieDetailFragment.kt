package me.htookyaw.movieapp.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import me.htookyaw.data.network.utils.Constants
import me.htookyaw.movieapp.GlideApp
import me.htookyaw.movieapp.R
import me.htookyaw.movieapp.databinding.FragmentMovieDetailBinding
import me.htookyaw.movieapp.ui.base.BaseFragment
import me.htookyaw.movieapp.ui.movielist.MovieUI
import me.htookyaw.movieapp.utils.observeResult

@AndroidEntryPoint
class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding>() {

    private val viewModel by viewModels<MovieDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        observeMovie()
    }

    override fun onDestroyView() {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        super.onDestroyView()
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieDetailBinding {
        return FragmentMovieDetailBinding.inflate(inflater, container, false)
    }

    private fun setUpUI() {
        binding.fab.setOnClickListener { viewModel.onFavoriteClicked() }
    }

    private fun observeMovie() {
        viewModel.movie.observeResult<MovieUI, Exception>(
            viewLifecycleOwner,
            onSuccess = {
                showData(it)
            },
            onError = {
                notify(it.message.orEmpty())
            }
        )
    }

    private fun showData(data: MovieUI) {
        binding.collapsingToolbarLayout.title = data.title
        binding.fab.setImageResource(
            if (data.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_round_favorite_border_24
        )
        binding.tvRating.text = data.voteAverage.toString()
        binding.info.tvOverview.text = data.overview
        GlideApp.with(this)
            .load(Constants.IMAGE_SIZE_ORIGINAL + data.backdropPath)
            .thumbnail(GlideApp.with(this).load(Constants.IMAGE_SIZE_154 + data.backdropPath))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.expandedImage)
        GlideApp.with(this)
            .load(Constants.IMAGE_SIZE_185 + data.posterPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.imageView)
    }
}
