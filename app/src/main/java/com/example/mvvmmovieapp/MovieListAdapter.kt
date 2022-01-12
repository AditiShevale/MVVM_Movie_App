package com.example.mvvmmovieapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mvvmmovieapp.databinding.ItemListBinding
import com.example.mvvmmovieapp.network.MovieList

class MovieListAdapter :
    ListAdapter<MovieList, MovieListAdapter.MoviesViewHolder>(DiffCallback) {

    class MoviesViewHolder(
        private var binding: ItemListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movielist: MovieList) {
            val imguri = movielist.image.toUri().buildUpon().scheme("https").build()
            binding.imgMovie.load(imguri)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieList>() {
        override fun areItemsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieList, newItem: MovieList): Boolean {
            return oldItem.image == newItem.image
        }
    }
}