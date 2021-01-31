package lt.todo.movieapi.ui.fragments.upcomingMovies

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_upcoming_movies.view.*
import lt.todo.movieapi.R
import lt.todo.movieapi.adapter.UpcomingMoviesAdapter
import lt.todo.movieapi.util.NetworkResult
import lt.todo.movieapi.viewModels.UpcomingMovieViewModel


class UpcomingMoviesFragment : Fragment() {

    private lateinit var upcomingMovieViewModel: UpcomingMovieViewModel
    private val mAdapter by lazy { UpcomingMoviesAdapter() }
    private lateinit var mView: View

    //private lateinit var adapter: UpcomingMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        upcomingMovieViewModel =
            ViewModelProvider(requireActivity()).get(UpcomingMovieViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_upcoming_movies, container, false)

        setupRecyclerView()
        requestApiData()

        return mView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun showLoadingDialog(){
        val builder = AlertDialog.Builder(this.context)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialogg,null)
        val message = dialogView.findViewById<TextView>(R.id.messageTv)
        message.text = "Loading..."
        builder.setView(dialogView)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        Handler().postDelayed({dialog.dismiss()},800)
    }

    private fun setupRecyclerView() {
        showLoadingDialog()
        mView.recyclerViewUpcoming.adapter = mAdapter
        mView.recyclerViewUpcoming.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        mView.recyclerViewUpcoming.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerViewUpcoming.hideShimmer()
    }

    private fun requestApiData() {
        upcomingMovieViewModel.getUpcomingMovies()
        upcomingMovieViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
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