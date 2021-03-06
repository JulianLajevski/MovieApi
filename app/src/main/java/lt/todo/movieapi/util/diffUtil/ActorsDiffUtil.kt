package lt.todo.movieapi.util.diffUtil

import androidx.recyclerview.widget.DiffUtil
import lt.todo.movieapi.data.network.models.actors.Cast

class ActorsDiffUtil(
    private val oldList: List<Cast>,
    private val newList: List<Cast>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}