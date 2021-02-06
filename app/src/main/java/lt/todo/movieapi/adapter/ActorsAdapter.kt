package lt.todo.movieapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lt.todo.movieapi.MovieDetailFragmentDirections
import lt.todo.movieapi.R
import lt.todo.movieapi.data.network.models.actors.Cast
import lt.todo.movieapi.data.network.models.actors.actorsResults
import lt.todo.movieapi.databinding.ActorsRowLayoutBinding
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.util.diffUtil.ActorsDiffUtil

class ActorsAdapter(): RecyclerView.Adapter<ActorsAdapter.MyViewHolder>() {

    private var actors = emptyList<Cast>()
    private val limit = 7

    class MyViewHolder(private val binding: ActorsRowLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Cast){
            binding.result = result
            binding.executePendingBindings()

            if(result.profilePath.isNullOrBlank()){
                binding.movieImageView.setImageResource(R.drawable.person_placeholder)
            }else{
                Glide.with(binding.movieImageView.context).load(Constants.IMAGE_URL+result.profilePath)
                        .into(binding.movieImageView)
            }

        }

        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ActorsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return ActorsAdapter.MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentActor = actors[position]
        holder.bind(currentActor)
    }

    override fun getItemCount(): Int {
        return if (actors.size > limit) {
            limit
        } else {
            actors.size
        }
    }

     fun setData(newData: actorsResults){
        val actorsDiffUtil = ActorsDiffUtil(actors, newData.cast)
        val diffUtilResults = DiffUtil.calculateDiff(actorsDiffUtil)
        actors = newData.cast
        diffUtilResults.dispatchUpdatesTo(this)
    }

}