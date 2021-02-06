package lt.todo.movieapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import lt.todo.movieapi.R
import lt.todo.movieapi.data.network.models.search.Result
import lt.todo.movieapi.data.network.models.search.SearchResults
import lt.todo.movieapi.databinding.SearchMovieRowLayoutBinding
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.diffUtil.SearchMoviesDiffUtil

class SearchMoviesAdapter : RecyclerView.Adapter<SearchMoviesAdapter.MyViewHolder>() {

    private var movies = emptyList<Result>()

    class MyViewHolder(private val binding: SearchMovieRowLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
                binding.result = result
                binding.executePendingBindings()

            binding.movieImageView.load(Constants.IMAGE_URL + result.posterPath) {
                crossfade(500)
                error(R.drawable.default_placeholder)
            }

        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchMovieRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMoviesAdapter.MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchMoviesAdapter.MyViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(newData: SearchResults){
        val searchMovieDIffUtil = SearchMoviesDiffUtil(movies, newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(searchMovieDIffUtil)
        movies = newData.results
        diffUtilResult.dispatchUpdatesTo(this)
    }
}