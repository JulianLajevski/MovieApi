package lt.todo.movieapi.ui.fragments.topRatedMovies

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_top_rated_movies.*
import kotlinx.android.synthetic.main.fragment_top_rated_movies.view.*
import lt.todo.movieapi.R
import lt.todo.movieapi.adapter.TopRatedMoviesAdapter
import lt.todo.movieapi.util.NetworkResult
import lt.todo.movieapi.viewModels.TopRatedViewModel


class TopRatedMoviesFragment : Fragment() {

    private lateinit var topRatedViewModel: TopRatedViewModel
    private val mAdapter by lazy { TopRatedMoviesAdapter() }
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topRatedViewModel = ViewModelProvider(requireActivity()).get(TopRatedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_top_rated_movies, container, false)

        setupRecyclerView()
        requestApiData()

        return mView
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

    private fun setupRecyclerView(){
        mView.recyclerViewTopRated.adapter = mAdapter
        mView.recyclerViewTopRated.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
        showLoadingDialog()
    }

    private fun showShimmerEffect() {
        mView.recyclerViewTopRated.showShimmer()
    }

    private fun hideShimmerEffect() {
        mView.recyclerViewTopRated.hideShimmer()
    }

    private fun requestApiData() {
        topRatedViewModel.getTopRatedMovies()
        topRatedViewModel.moviesResponse.observe(viewLifecycleOwner, { response ->
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