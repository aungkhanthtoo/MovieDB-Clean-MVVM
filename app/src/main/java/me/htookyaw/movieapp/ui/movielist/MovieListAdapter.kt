package me.htookyaw.movieapp.ui.movielist

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.htookyaw.movieapp.GlideRequests
import me.htookyaw.movieapp.R
import me.htookyaw.movieapp.utils.inflate

class MovieListAdapter(private val glide: GlideRequests) :
    ListAdapter<MovieUI, MovieViewHolder>(DIFF) {

    var listener: Listener? = null

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<MovieUI>() {

            override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(parent.inflate(R.layout.item_grid_movie), glide).apply {
            itemView.setOnClickListener {
                listener?.onItemClick(
                    currentList[adapterPosition],
                    itemView
                )
            }
            binding.btnFav.setOnClickListener { listener?.onFavoriteToggle(currentList[adapterPosition]) }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    interface Listener {

        fun onItemClick(data: MovieUI, view: View)

        fun onFavoriteToggle(data: MovieUI)
    }
}
