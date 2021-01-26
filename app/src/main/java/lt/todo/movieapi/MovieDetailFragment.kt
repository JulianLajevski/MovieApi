package lt.todo.movieapi
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private fun setupObserver() {
        detailViewModel.movieDetails.observe(viewLifecycleOwner, {

            Glide.with(movieImageView.context).load(Constants.IMAGE_URL+it.posterPath)
                .into(movieImageView)

            var genre: String ?= ""

               movieTitleTextView.text = it.title
//                movieDescription.text = it.overview

            movieRatingBar.rating = it.voteAverage.toFloat()/2
            movieRatingTextView.text = it.voteAverage.toFloat().toString() + "/10"

            for (element in it.genres) {
                genre += element.name + ", "
            }

            movieGenresTextView.text = genre
            movieDateTextView.text = it.releaseDate
//            movieDurationTextView.text = it.runtime.toString() + " min"
//            movieLanguageTextView.text = it.spokenLanguages[0].englishName
//            if(it.budget == 0){
//                movieBudgetTextView.text = "-"
//                movieBudgetTextView.height = 70
//            }else{
//                movieBudgetTextView.text = it.budget.toString() + "$"
//            }
//            movieVotesTextView.text = it.voteCount.toString()

        })
    }
}