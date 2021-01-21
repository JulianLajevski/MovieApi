package lt.todo.movieapi

import android.opengl.Visibility
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.android.synthetic.main.fragment_search_movie.view.*
import kotlinx.android.synthetic.main.search_movie_row_layout.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lt.todo.movieapi.adapter.SearchMoviesAdapter
import lt.todo.movieapi.util.NetworkResult
import lt.todo.movieapi.viewModels.SearchQueryViewModel
import lt.todo.movieapi.viewModels.SearchViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchMovieFragment : Fragment(), SearchView.OnQueryTextListener{

    private lateinit var searchMovieViewModel: SearchViewModel
    private lateinit var searchMovieQueryViewModel: SearchQueryViewModel
    private val mAdapter by lazy { SearchMoviesAdapter() }
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchMovieViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        searchMovieQueryViewModel = ViewModelProvider(requireActivity()).get(SearchQueryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search_movie, container, false)

        setupRecyclerView()

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        searchMovieImage.visibility = View.VISIBLE
        searchMovieTextView.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            requestApiData(query)
        }
        searchMovieImage.visibility = View.INVISIBLE
        searchMovieTextView.visibility = View.INVISIBLE
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    private fun requestApiData(searchQuery: String){
        searchMovieViewModel.getSearchedMovies(searchMovieQueryViewModel.applyQueries(searchQuery))
        searchMovieViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    val snackbar: Snackbar = Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun setupRecyclerView(){
        mView.recyclerViewSearch.adapter = mAdapter
        mView.recyclerViewSearch.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun showShimmerEffect() {
        mView.recyclerViewSearch.showShimmer()
    }

    private fun hideShimmerEffect(){
        mView.recyclerViewSearch.hideShimmer()
    }




}