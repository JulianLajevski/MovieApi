package lt.todo.movieapi.bindingadapters

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import lt.todo.movieapi.R


class MoviesRowBinding {

    companion object{

//        @BindingAdapter("android:src")
//        fun setImageViewResource(imageView: ImageView, resource: Int) {
//            imageView.setImageResource(resource)
//        }


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