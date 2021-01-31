package lt.todo.movieapi.ui.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.todkars.shimmer.ShimmerRecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.android.synthetic.main.fragment_popular_movies.view.*
import kotlinx.android.synthetic.main.fragment_upcoming_movies.view.*
import lt.todo.movieapi.R
import lt.todo.movieapi.adapter.homeAdapters.HomePopularMovieAdapter
import lt.todo.movieapi.adapter.homeAdapters.HomeTopRatedMovieAdapter
import lt.todo.movieapi.adapter.homeAdapters.HomeUpcomingMovieAdapter
import lt.todo.movieapi.util.NetworkResult
import lt.todo.movieapi.viewModels.PopularMovieViewModel
import lt.todo.movieapi.viewModels.TopRatedViewModel
import lt.todo.movieapi.viewModels.UpcomingMovieViewModel


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    //View models
    private lateinit var upcomingViewModel: UpcomingMovieViewModel
    private lateinit var popularViewModel: PopularMovieViewModel
    private lateinit var topRatedViewModel: TopRatedViewModel

    //Adapters
    private val upcomingAdapter by lazy { HomeUpcomingMovieAdapter() }
    private val popularAdapter by lazy { HomePopularMovieAdapter() }
    private val topRatedAdapter by lazy { HomeTopRatedMovieAdapter() }

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        upcomingViewModel = ViewModelProvider(requireActivity()).get(UpcomingMovieViewModel::class.java)
        popularViewModel = ViewModelProvider(requireActivity()).get(PopularMovieViewModel::class.java)
        topRatedViewModel = ViewModelProvider(requireActivity()).get(TopRatedViewModel::class.java)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_movies, container, false)

        setupRecyclerView()
        requestApiData()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upcomingSeeAllButton.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToUpcomingMoviesFragment()
            upcomingSeeAllButton.findNavController().navigate(action)
        }

        popularSeeAllButton.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToPopularMoviesFragment()
            popularSeeAllButton.findNavController().navigate(action)
        }

        topRatedSeeAllButton.setOnClickListener {
            val action = MoviesFragmentDirections.actionMoviesFragmentToTopRatedMoviesFragment()
            topRatedSeeAllButton.findNavController().navigate(action)
        }

    }


    private fun setupRecyclerView(){
        setupUpcomingRecyclerView()
        setupPopularRecyclerView()
        setupTopRatedRecyclerView()
    }

    private fun requestApiData(){
        requestUpcomingApiData()
        requestPopularApiData()
        requestTopRatedApiData()
    }

    private fun setupUpcomingRecyclerView(){
        mView.upcomingRecyclerView.adapter = upcomingAdapter
        mView.upcomingRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        showShimmer(mView.upcomingRecyclerView)
    }

    private fun setupPopularRecyclerView(){
        mView.popularRecyclerView.adapter = popularAdapter
        mView.popularRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmer(mView.popularRecyclerView)
    }

    private fun setupTopRatedRecyclerView(){
        mView.topRatedRecyclerView.adapter = topRatedAdapter
        mView.topRatedRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        showShimmer(mView.topRatedRecyclerView)
    }

    fun showShimmer(recyclerView: ShimmerRecyclerView){
        recyclerView.showShimmer()
    }

    fun hideShimmer(recyclerView: ShimmerRecyclerView){
        recyclerView.hideShimmer()
    }

    private fun requestUpcomingApiData(){
        upcomingViewModel.getUpcomingMovies()
        upcomingViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer(mView.upcomingRecyclerView)
                    response.data?.let { upcomingAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer(mView.upcomingRecyclerView)
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer(mView.upcomingRecyclerView)
                }
            }
        })
    }

    private fun requestPopularApiData(){
        popularViewModel.getPopularMovies()
        popularViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer(mView.popularRecyclerView)
                    response.data?.let { popularAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer(mView.popularRecyclerView)
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer(mView.popularRecyclerView)
                }
            }
        })
    }

    private fun requestTopRatedApiData(){
        topRatedViewModel.getTopRatedMovies()
        topRatedViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer(mView.topRatedRecyclerView)
                    response.data?.let { topRatedAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer(mView.topRatedRecyclerView)
                    Toast.makeText(
                            requireContext(),
                            response.message.toString(),
                            Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmer(mView.topRatedRecyclerView)
                }
            }
        })
    }
}