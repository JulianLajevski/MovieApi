package lt.todo.movieapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.todo.movieapi.data.network.models.upcoming.Result
import lt.todo.movieapi.data.network.models.upcoming.UpcomingResults
import lt.todo.movieapi.databinding.MovieRowLayoutBinding
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.MoviesDiffUtil

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    private var movies = emptyList<Result>()

    class MyViewHolder(private val binding: MovieRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(result: Result){
                binding.result = result
                binding.executePendingBindings()
                Glide.with(binding.movieImageView.context).load(Constants.IMAGE_URL+result.posterPath)
                    .into(binding.movieImageView)
            }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieRowLayoutBinding.inflate(layoutInflater, parent, false)
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

    fun setData(newData: UpcomingResults){
        val moviesDiffUtil = MoviesDiffUtil(movies, newData.results)
        val diffUtilResults = DiffUtil.calculateDiff(moviesDiffUtil)
        movies = newData.results
        diffUtilResults.dispatchUpdatesTo(this)

    }

}