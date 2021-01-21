package lt.todo.movieapi.ui.fragments.popularMovies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.android.synthetic.main.fragment_popular_movies.view.*
import kotlinx.android.synthetic.main.fragment_upcoming_movies.view.recyclerViewUpcoming
import lt.todo.movieapi.viewModels.PopularMovieViewModel
import lt.todo.movieapi.R
import lt.todo.movieapi.adapter.PopularMoviesAdapter
import lt.todo.movieapi.util.NetworkResult


class PopularMoviesFragment : Fragment() {

    private lateinit var popularMovieViewModel: PopularMovieViewModel
    private val mAdapter by lazy { PopularMoviesAdapter() }
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularMovieViewModel =
            ViewModelProvider(requireActivity()).get(PopularMovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_popular_movies, container, false)

        setupRecyclerView()
        requestApiData()

        return mView
    }

    private fun setupRecyclerView() {
        mView.recyclerViewPopular.adapter = mAdapter
        mView.recyclerViewPopular.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        mView.recyclerViewPopular.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerViewPopular.hideShimmer()
    }

    private fun requestApiData() {
        popularMovieViewModel.getPopularMovies()
        popularMovieViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

}