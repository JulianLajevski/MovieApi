package lt.todo.movieapi.adapter.homeAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.todo.movieapi.data.network.models.upcoming.Result
import lt.todo.movieapi.data.network.models.upcoming.UpcomingResults
import lt.todo.movieapi.databinding.HomeUpcomingMovieRowLayoutBinding
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.diffUtil.UpcomingMoviesDiffUtil

class HomeUpcomingMovieAdapter() : RecyclerView.Adapter<HomeUpcomingMovieAdapter.MyViewHolder>(){

    private var movies = emptyList<Result>()
    private val limit = 5

    class MyViewHolder(private val binding: HomeUpcomingMovieRowLayoutBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
            Glide.with(binding.movieImageView.context).load(Constants.IMAGE_URL + result.posterPath)
                    .into(binding.movieImageView)
        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HomeUpcomingMovieRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeUpcomingMovieAdapter.MyViewHolder {
        return HomeUpcomingMovieAdapter.MyViewHolder.from(parent)
    }


    override fun getItemCount(): Int {
        return if (movies.size > limit) {
            limit
        } else {
            movies.size
        }
    }

    fun setData(newData: UpcomingResults){
        val moviesDiffUtil = UpcomingMoviesDiffUtil(movies, newData.results)
        val diffUtilResults = DiffUtil.calculateDiff(moviesDiffUtil)
        movies = newData.results
        diffUtilResults.dispatchUpdatesTo(this)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }


}