package lt.todo.movieapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import lt.todo.movieapi.R
import lt.todo.movieapi.data.network.models.popular.PopularResults
import lt.todo.movieapi.data.network.models.popular.Result
import lt.todo.movieapi.databinding.PopularMovieRowLayoutBinding
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.diffUtil.PopularMoviesDiffUtil
import lt.todo.movieapi.util.diffUtil.UpcomingMoviesDiffUtil

class PopularMoviesAdapter: RecyclerView.Adapter<PopularMoviesAdapter.MyViewHolder>()  {

    private var movies = emptyList<Result>()

    class MyViewHolder(private val binding: PopularMovieRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()

            binding.movieImageView.load(Constants.IMAGE_URL + result.posterPath) {
                crossfade(500)
                error(R.drawable.default_placeholder)
            }
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PopularMovieRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setData(newData: PopularResults){
        val moviesDiffUtil = PopularMoviesDiffUtil(movies, newData.results)
        val diffUtilResults = DiffUtil.calculateDiff(moviesDiffUtil)
        movies = newData.results
        diffUtilResults.dispatchUpdatesTo(this)

    }
}