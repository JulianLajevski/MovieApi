package lt.todo.movieapi.ui.fragments.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.Window
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.navArgs
import coil.load
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_movie_details.toolbar
import kotlinx.android.synthetic.main.fragment_movie_detail_fragment.*
import kotlinx.android.synthetic.main.fragment_movies.*
import lt.todo.movieapi.MovieDetailFragment
import lt.todo.movieapi.R
import lt.todo.movieapi.adapter.ViewPagerAdapter
import lt.todo.movieapi.ui.fragments.popularMovies.PopularMoviesFragment
import lt.todo.movieapi.ui.fragments.topRatedMovies.TopRatedMoviesFragment
import lt.todo.movieapi.ui.fragments.upcomingMovies.UpcomingMoviesFragment
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.viewModels.MovieDetailViewModel
import java.util.ArrayList

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val args by navArgs<MovieDetailsActivityArgs>()
    private val detailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val window: Window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        val movieId = args.result

       detailViewModel.fetchMovieDetails(movieId.toString())
        setupObserver()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupObserver() {
        detailViewModel.movieDetails.observe(this, {
            Glide.with(movieDetailsImage.context).load(Constants.IMAGE_URL+it.backdropPath)
                .into(movieDetailsImage)
        })
    }
}