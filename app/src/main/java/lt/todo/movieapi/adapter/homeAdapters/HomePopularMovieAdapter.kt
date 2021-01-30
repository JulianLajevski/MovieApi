package lt.todo.movieapi.adapter.homeAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.todo.movieapi.adapter.PopularMoviesAdapter
import lt.todo.movieapi.adapter.UpcomingMoviesAdapter
import lt.todo.movieapi.data.network.models.popular.PopularResults
import lt.todo.movieapi.data.network.models.popular.Result
import lt.todo.movieapi.databinding.HomePopularMovieRowLayoutBinding

import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.diffUtil.PopularMoviesDiffUtil
import lt.todo.movieapi.util.diffUtil.UpcomingMoviesDiffUtil

class HomePopularMovieAdapter() : RecyclerView.Adapter<HomePopularMovieAdapter.MyViewHolder>() {

    private var movies = emptyList<Result>()
    private val limit = 2

    class MyViewHolder(private val binding: HomePopularMovieRowLayoutBinding):
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
                val binding = HomePopularMovieRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePopularMovieAdapter.MyViewHolder {
        return HomePopularMovieAdapter.MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return if (movies.size > limit) {
            limit
        } else {
            movies.size
        }
    }

    fun setData(newData: PopularResults){
        val moviesDiffUtil = PopularMoviesDiffUtil(movies, newData.results)
        val diffUtilResults = DiffUtil.calculateDiff(moviesDiffUtil)
        movies = newData.results
        diffUtilResults.dispatchUpdatesTo(this)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }


}