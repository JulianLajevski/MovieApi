package lt.todo.movieapi.ui.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movies.*
import lt.todo.movieapi.R
import lt.todo.movieapi.adapter.PopularMoviesAdapter
import lt.todo.movieapi.ui.fragments.upcomingMovies.UpcomingMoviesFragment
import lt.todo.movieapi.adapter.ViewPagerAdapter
import lt.todo.movieapi.ui.fragments.favorites.FavoriteMovieFragment
import lt.todo.movieapi.ui.fragments.popularMovies.PopularMoviesFragment
import lt.todo.movieapi.ui.fragments.topRatedMovies.TopRatedMoviesFragment


@AndroidEntryPoint
class MoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = activity?.supportFragmentManager?.let {
            ViewPagerAdapter(supportFragmentManager = it)
        }
        adapter?.addFragment(UpcomingMoviesFragment(), "Upcoming")
        adapter?.addFragment(PopularMoviesFragment(), "Popular")
        adapter?.addFragment(TopRatedMoviesFragment(), "Top")

        viewPager.adapter = adapter

        tabs.setupWithViewPager(viewPager)
    }

}