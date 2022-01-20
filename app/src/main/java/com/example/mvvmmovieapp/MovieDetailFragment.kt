package com.example.mvvmmovieapp

import android.app.Application
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mvvmmovieapp.databinding.FragmentMovieDetailBinding
import com.example.mvvmmovieapp.databinding.FragmentMovieListBinding
import com.example.mvvmmovieapp.network.MovieBackDropList
import com.example.mvvmmovieapp.network.MovieList
import com.example.mvvmmovieapp.viewmodel.MovieViewModel
import com.example.mvvmmovieapp.viewmodel.MovieViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailFragment : Fragment() {

    private val args: MovieDetailFragmentArgs by navArgs()

    private var binding: FragmentMovieDetailBinding? = null

    private lateinit var movieData: MovieList

    private val viewModel: MovieViewModel by activityViewModels {
        MovieViewModelFactory(
            (activity?.application as MovieApplication).database.movieDao(),
            activity?.application as Application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        binding?.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieData = args.movieData
        viewModel.getImageBackDrop(movieData.id)
        viewModel.poster_status.observe(viewLifecycleOwner, {
            val backdropUri = it.backdrops.random()
                .link.toUri().buildUpon().scheme("https").build()
            binding?.imgBackDrop?.load(backdropUri)
        })
        binding?.btnFav?.setOnClickListener {
            viewModel.InsertFav(movieData)

        }
        binding?.btnDelete?.setOnClickListener {
            viewModel.DeleteFav(movieData)
        }
        viewModel.movieReminder()
    }

}