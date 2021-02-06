package lt.todo.movieapi

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.Window
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_actors_detail.*
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.activity_movie_details.toolbar
import lt.todo.movieapi.util.Constants
import lt.todo.movieapi.viewModels.ActorsDetailViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.Calendar.getInstance

@AndroidEntryPoint
class ActorsDetailActivity : AppCompatActivity() {

    private val args by navArgs<ActorsDetailActivityArgs>()
    private val actorsViewModel: ActorsDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actors_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val actorId = args.result.id

        val window: Window = this.window
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        actorsViewModel.fetchActorDetails(actorId.toString())

        setupObserver()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupObserver(){
        actorsViewModel.actorDetails.observe(this, {
            actorNameTextView.text = it.name
            actorsMainImage.load(Constants.IMAGE_URL + it.profilePath) {
                crossfade(500)
                error(R.drawable.default_placeholder)
            }

            if(it.biography.isNullOrEmpty()){
                actorBiography.text = "N/A"
            }else{
                actorBiography.text = it.biography
            }

            if(it.birthday.isNullOrEmpty()){
                actorBornTextView.text = "N/A"
            }else{
                actorBornTextView.text = it.birthday
            }

            if(it.placeOfBirth.isNullOrEmpty()){
                actorBirthplaceTextView.text = "N/A"
            }else{
                actorBirthplaceTextView.text = it.placeOfBirth
            }

            if(it.knownForDepartment.isNullOrEmpty()){
                actorActivityTextView.text = "N/A"
            }else{
                actorActivityTextView.text = it.knownForDepartment
            }

            var alsoKnow: String ?= ""
            if(it.alsoKnownAs.isNullOrEmpty()){
                alsoKnowTextView.text = "N/A"
            }else{
                for (element in it.alsoKnownAs) {
                    if(element == it.alsoKnownAs[it.alsoKnownAs.size-1]){
                        alsoKnow += element
                    }else{
                        alsoKnow += element + "/ "
                    }
                }
                alsoKnowTextView.text = alsoKnow
            }

            if(it.birthday.isNullOrBlank()){
                actorsAgeTextView.text = "N/A"
            }
            else{
                var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                var date = LocalDate.parse(it.birthday, formatter)
                val today = LocalDate.now()
                val age: String = (today.year - date.year).toString()
                actorsAgeTextView.text = age
            }

            backgroundActorImageView.load(Constants.IMAGE_URL + args.result.profilePath) {
                crossfade(500)
                error(R.drawable.default_placeholder)
            }

        })

    }


}