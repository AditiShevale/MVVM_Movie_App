package com.example.mvvmmovieapp

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.mvvmmovieapp.databinding.FragmentMovieDetailBinding
import com.example.mvvmmovieapp.network.MovieFullData
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
            (activity?.application as MovieApplication).repository,
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
        viewModel.poster_status.observe(viewLifecycleOwner) {
            val backdropUri = it.backdrops.random()
                .link.toUri().buildUpon().scheme("https").build()
            binding?.imgBackDrop?.load(backdropUri)
        }
        binding?.btnFav?.setOnClickListener {
            viewModel.insertFav(movieData)

        }
        binding?.btnDelete?.setOnClickListener {
            viewModel.deleteFav(movieData)
        }
        viewModel.movieReminder()
        viewModel.detailData()

        viewModel.detailData_status.observe(viewLifecycleOwner) {
            binding?.txtRating?.text = it.imDbRating
            binding?.txtPlot?.text = it.plot
            binding?.txtCrew?.text = it.actorList[0].name
        }


    }

}