package com.example.mvvmmovieapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.example.mvvmmovieapp.MovieApplication
import com.example.mvvmmovieapp.MovieListAdapter
import com.example.mvvmmovieapp.R
import com.example.mvvmmovieapp.databinding.FragmentMovieListBinding
import com.example.mvvmmovieapp.viewmodel.MovieViewModel
import com.example.mvvmmovieapp.viewmodel.MovieViewModelFactory


class MovieListFragment : Fragment() {
    private val viewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory(
            (activity?.application as MovieApplication).database.movieDao()
        )
    }
    lateinit var binding: FragmentMovieListBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE) ?: return

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.sharedPref(sharedPreferences.getString("key_movie", "mostPopular"))
        val adapter = MovieListAdapter {
            val action =
                MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = adapter

        viewModel.status.observe(viewLifecycleOwner, {
            adapter.submitList(it.items)
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.mostPopular -> {
                viewModel.getMovieList()
                sharedPreferences.edit().putString("key_movie", "mostPopular").apply()
                true
            }
            R.id.comingSoon -> {
                viewModel.getComingSoonList()
                sharedPreferences.edit().putString("key_movie", "comingSoon").apply()
                true
            }
            R.id.favorite -> {
                viewModel.getfavMovieList()
                sharedPreferences.edit().putString("key_movie","favorite").apply()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}