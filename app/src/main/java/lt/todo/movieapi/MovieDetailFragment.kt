package lt.todo.movieapi
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_detail_fragment.*
import kotlinx.android.synthetic.main.fragment_movie_detail_fragment.view.*
import kotlinx.android.synthetic.main.fragment_movies.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lt.todo.movieapi.adapter.ActorsAdapter
import lt.todo.movieapi.adapter.homeAdapters.HomePopularMovieAdapter
import lt.todo.movieapi.data.network.models.moviedetails.MovieDetail
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.NetworkResult
import lt.todo.movieapi.viewModels.ActorsViewModel
import lt.todo.movieapi.viewModels.MovieDetailViewModel
import lt.todo.movieapi.viewModels.UpcomingMovieViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MovieDetailFragment : Fragment() {

   // private val detailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var detailViewModel: MovieDetailViewModel
    private lateinit var actorsViewModel: ActorsViewModel
    var id: Int? = null

    private val actorsAdapter by lazy { ActorsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel =
            ViewModelProvider(requireActivity()).get(MovieDetailViewModel::class.java)

        actorsViewModel =
            ViewModelProvider(requireActivity()).get(ActorsViewModel::class.java)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_movie_detail_fragment, container, false)
        setupObserver()



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupObserver() {
        detailViewModel.movieDetails.observe(viewLifecycleOwner, {

            movieImageView.load(Constants.IMAGE_URL + it.posterPath) {
                crossfade(500)
                error(R.drawable.default_placeholder)
            }

            var genre: String ?= ""

                movieTitleTextView.text = it.title
                movieDescription.text = it.overview

            movieRatingBar.rating = it.voteAverage.toFloat()/2
            movieRatingTextView.text = it.voteAverage.toFloat().toString() + "/10"

            for (element in it.genres) {
                if(element == it.genres[it.genres.size-1]){
                    genre += element.name
                }else{
                    genre += element.name + "/ "
                }
            }

            movieGenresTextView.text = genre


            var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            var date = LocalDate.parse(it.releaseDate, formatter)

            movieDateTextView.text = date.year.toString()
//            movieDurationTextView.text = it.runtime.toString() + " min"
            originalTitleTextView.text = it.originalTitle
            originalLanguageTextView.text = it.originalLanguage
            statusTextView.text = it.status

            if(it.budget.toString() != "0"){
                budgetTextView.text = "$" + it.budget.toString()
            }else{
                budgetTextView.text = "N/A"
            }

            if(it.revenue.toString() != "0"){
                revenueTextView.text = "$" + it.revenue.toString()
            }else{
                revenueTextView.text = "N/A"
            }

            releaseDateTextView.text = it.releaseDate

            requestActorsApiData(it.id.toString())
            setupActorsRecyclerView()


        })
    }


    private fun setupActorsRecyclerView(){
        actorsRecyclerView.adapter = actorsAdapter
        actorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
    private fun requestActorsApiData(movieId: String){

        actorsViewModel.getMoviesActors(movieId)
        actorsViewModel.actorsResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let { actorsAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    val snackbar: Snackbar = Snackbar.make(requireView(), response.message.toString(), Snackbar.LENGTH_SHORT)
                    snackbar.show()
                }
            }
        })
    }
}