package lt.todo.movieapi.bindingadapters

import android.util.Log
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import lt.todo.movieapi.R
import lt.todo.movieapi.SearchMovieFragmentDirections
import lt.todo.movieapi.ui.fragments.movies.MoviesFragmentDirections


class MoviesRowBinding {

    companion object{

//        @BindingAdapter("android:src")
//        fun setImageViewResource(imageView: ImageView, resource: Int) {
//            imageView.setImageResource(resource)
//        }

        @BindingAdapter("onMovieClickListener")
        @JvmStatic
        fun onMovieClickListener(movieRowLayout: ConstraintLayout, id: Int){
            movieRowLayout.setOnClickListener {
                try {
                    val action =
                        MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsActivity(id)
                    movieRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d("OnMovieClickListener", e.message.toString())
                }

            }
        }

        @BindingAdapter("onSearchMovieClickListener")
        @JvmStatic
        fun onSearchMovieClickListener(movieRowLayout: ConstraintLayout, id: Int){
            movieRowLayout.setOnClickListener {
                try{
                    val action =
                        SearchMovieFragmentDirections.actionSearchMovieFragmentToMovieDetailsActivity(id)
                    movieRowLayout.findNavController().navigate(action)
                }catch (e: Exception){
                    Log.d("OnSearchMovieListener", e.message.toString())
                }
            }
        }

        @BindingAdapter("setRatingBar")
        @JvmStatic
        fun setRatingBar(ratingBar: RatingBar, votes: Double){
            ratingBar.rating = votes.toFloat()
        }

        @BindingAdapter("setNumberOfVotes")
        @JvmStatic
        fun setNumberOfVotes(textView: TextView, votes: Int){
            textView.text = votes.toString()
        }

        @BindingAdapter("setNumberOfVotesAverage")
        @JvmStatic
        fun setNumberOfVotesAverage(textView: TextView, avgVotes: Double){
            textView.text = avgVotes.toString()
        }

        @BindingAdapter("loadImageFromUrl")
        fun loadImageFromUrl(imageView: ImageView, url: String){
            imageView.load(url){
                crossfade(500)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }

}