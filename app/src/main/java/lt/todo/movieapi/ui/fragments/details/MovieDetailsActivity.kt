package lt.todo.movieapi.ui.fragments.details

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_movie_details.toolbar
import lt.todo.movieapi.R
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.viewModels.MovieDetailViewModel

@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {

    private val args by navArgs<MovieDetailsActivityArgs>()
    private val detailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        showLoadingDialog()
        navController = findNavController(R.id.movieDetailsViewPager)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true);


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

    private fun showLoadingDialog(){
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.progress_dialogg,null)
        val message = dialogView.findViewById<TextView>(R.id.messageTv)
        message.text = "Loading..."
        builder.setView(dialogView)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        Handler().postDelayed({dialog.dismiss()},800)
    }

    private fun setupObserver() {
        detailViewModel.movieDetails.observe(this, {
            MovieBackgroundImage.load(Constants.IMAGE_URL + it.backdropPath) {
                crossfade(500)
                error(R.color.pink)
            }
        })
    }
}