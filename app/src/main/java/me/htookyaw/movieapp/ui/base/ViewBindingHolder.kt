package me.htookyaw.movieapp.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class ViewBindingHolder<VB : ViewBinding>(binding: VB) :
    RecyclerView.ViewHolder(binding.root) {

    private var _binding: VB? = null

    init {
        _binding = binding
    }

    val binding
        get() = _binding!!
}
