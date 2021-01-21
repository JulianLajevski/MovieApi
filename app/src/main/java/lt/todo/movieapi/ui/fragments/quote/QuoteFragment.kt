package lt.todo.movieapi.ui.fragments.quote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_quote.*
import lt.todo.movieapi.R
import java.util.ArrayList


class QuoteFragment : Fragment() {

    private var quotesList = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quote, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addQuotesToList(quotesList)
        getRandomQuote(quotesList)
    }

    private fun getRandomQuote(list: ArrayList<String>){
        val randomString: String = List(1) {
            list.random()
        }.joinToString("")

        quotesTextView.text = randomString
    }

    private fun addQuotesToList(list: ArrayList<String>){
        list.add("\"Just keep swimming\" - Finding Nemo, 2003")
        list.add("\"I wish I knew how to quit you\" - Brokeback Mountain, 2005")
        list.add("\"What's the most you ever lost on a coin toss?\" - No Country for Old Men, 2007")
        list.add("\"I drink your milkshake\" - There Will Be Blood, 2007")
        list.add("\"Why so serious?\" - The Dark Knight, 2008")
        list.add("“A million dollars isn't cool. You know what's cool? A billion dollars.” - The Social Network, 2010")
        list.add("\"Frankly, my dear, I don't give a damn.\" - Gone With the Wind, 1939")
        list.add("\"I'm going to make him an offer he can't refuse.\" - The Godfather, 1972")
        list.add("Fun fact: This line makes it into each Godfather film in some way or another.")
        list.add("\"You don't understand! I coulda had class. I coulda been a contender. I could've been somebody, instead of a bum, which is what I am.\" - On the Waterfront, 1954")
        list.add("\"Toto, I've got a feeling we're not in Kansas anymore.\" - The Wizard of Oz, 1939")
        list.add("\"Here's looking at you, kid.\" - Casablanca, 1942")
        list.add("\"Go ahead, make my day.\" - Sudden Impact, 1983")
        list.add("\"All right, Mr. DeMille, I'm ready for my close-up.\" - Sunset Boulevard, 1950")
        list.add("\"May the Force be with you.\" - Star Wars, 1977")
        list.add("\"Fasten your seatbelts. It's going to be a bumpy night.\" - All About Eve, 1950")
        list.add("\"You talking to me?\" - Taxi Driver, 1976")
        list.add("Fun fact: Robert DeNiro improvised this line. The script only said \"Travis speaks to himself in the mirror\" so DeNiro took some liberties and was ultimately successful.")
        list.add("\"What we've got here is failure to communicate.\" - Cool Hand Luke, 1967")
        list.add("\"I love the smell of napalm in the morning.\" - Apocalypse Now, 1979")
        list.add("\"Love means never having to say you're sorry.\" - Love Story, 1970")
        list.add("\"The stuff that dreams are made of.\" - The Maltese Falcon, 1941")
        list.add("\"E.T. phone home.\" - E.T. The Extra-Terrestrial, 1982")
        list.add("\"They call me Mister Tibbs!\" - In the Heat of the Night, 1967")
        list.add("\"Rosebud.\" - Citizen Kane, 1941")
        list.add("\"Made it, Ma! Top of the world!\" - White Heat, 1949\n")
        list.add("\"Louis, I think this is the beginning of a beautiful friendship.\" - Casablanca, 1942")
        list.add("\"A census taker once tried to test me. I ate his liver with some fava beans and a nice Chianti.\" - The Silence of the Lambs, 1991\n")
        list.add("\"Bond. James Bond.\" - Dr. No, 1962")
        list.add("\"There's no place like home.\" - The Wizard of Oz, 1939")
        list.add("\"I am big! It's the pictures that got small.\" - Sunset Blvd., 1950")
        list.add("\"Show me the money!\" - Jerry Maguire, 1996")
        list.add("\"Why don't you come up sometime and see me?\" - She Done Him Wrong, 1933\n")
        list.add("\"I'll be back.\" - The Terminator, 1984")
        list.add("Fun fact: When Haley Joel Osmet says this line the camera goes to Bruce Willis's face. This is a cinematic clue that Bruce Willis's character is dead.")
        list.add("Fun fact: The original line was \"It's alive! It's alive! In the name of God! Now I know what it's like to be God!\" Censors cut Dr. Frankenstein's full line because it was considered sacrilege.")


    }

}