package lt.todo.movieapi
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.fragment_movie_detail_fragment.*
import kotlinx.android.synthetic.main.fragment_movie_detail_fragment.view.*
import lt.todo.movieapi.data.network.models.moviedetails.MovieDetail
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.viewModels.MovieDetailViewModel
import lt.todo.movieapi.viewModels.UpcomingMovieViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MovieDetailFragment : Fragment() {

   // private val detailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var detailViewModel: MovieDetailViewModel
    var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel =
            ViewModelProvider(requireActivity()).get(MovieDetailViewModel::class.java)
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
     //   setupObserver()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupObserver() {
        detailViewModel.movieDetails.observe(viewLifecycleOwner, {

            Glide.with(movieImageView.context).load(Constants.IMAGE_URL+it.posterPath)
                .into(movieImageView)

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
            budgetTextView.text = "$" + it.budget.toString()
            revenueTextView.text = "$" + it.revenue.toString()
            releaseDateTextView.text = it.releaseDate
//            if(it.budget == 0){
////                movieBudgetTextView.text = "-"
////                movieBudgetTextView.height = 70
//            }else{
//                movieBudgetTextView.text = it.budget.toString() + "$"
//            }
//            movieVotesTextView.text = it.voteCount.toString()

        })
    }
}