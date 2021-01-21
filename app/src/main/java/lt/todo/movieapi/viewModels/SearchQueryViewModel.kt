package lt.todo.movieapi.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class SearchQueryViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(query: String): String {

        val queries: String = query

        return queries
    }
}