package me.htookyaw.movieapp.ui.movielist

import android.view.View
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import me.htookyaw.data.network.utils.Constants
import me.htookyaw.movieapp.GlideApp
import me.htookyaw.movieapp.GlideRequests
import me.htookyaw.movieapp.R
import me.htookyaw.movieapp.databinding.ItemGridMovieBinding
import me.htookyaw.movieapp.ui.base.ViewBindingHolder

class MovieViewHolder(itemView: View, glideRequests: GlideRequests) :
    ViewBindingHolder<ItemGridMovieBinding>(ItemGridMovieBinding.bind(itemView)) {

    fun bind(data: MovieUI) = with(binding) {
        GlideApp.with(itemView.context).load(Constants.IMAGE_SIZE_185 + data.posterPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
        ivFav.setImageResource(
            if (data.favorite) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_round_favorite_border_24
        )
    }
}
