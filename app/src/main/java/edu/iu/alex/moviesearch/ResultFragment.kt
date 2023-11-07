package edu.iu.alex.moviesearch

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide

class ResultFragment : Fragment() {

    private val viewModel: ViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val posterImage = view.findViewById<ImageView>(R.id.ivPoster)
        val movieTitle = view.findViewById<TextView>(R.id.tvTitle)
        val movieRating = view.findViewById<TextView>(R.id.tvRating)
        val movieYear = view.findViewById<TextView>(R.id.tvYear)
        val movieRuntime = view.findViewById<TextView>(R.id.tvRuntime)
        val movieGenre = view.findViewById<TextView>(R.id.tvGenre)
        val movieImdbRating = view.findViewById<TextView>(R.id.tvImdbRating)
        val movieLink = view.findViewById<TextView>(R.id.tvImdbLink)
        val buttonShare = view.findViewById<ImageButton>(R.id.btnShare)


        viewModel.movieData.observe(viewLifecycleOwner) { movieResponse ->

            Log.d("Result Fragment", "Received data ${movieResponse.Title}")

            movieTitle.text = movieResponse.Title
            movieRating.text = movieResponse.Rated
            movieYear.text = movieResponse.Year
            movieRuntime.text = movieResponse.Runtime
            movieGenre.text = movieResponse.Genre
            movieImdbRating.text = movieResponse.imdbRating

            //Load poster with glide.
            Glide.with(this)
                .load(movieResponse.Poster)
                .into(posterImage)

            posterImage.visibility = View.VISIBLE
            movieTitle.visibility = View.VISIBLE
            movieRating.visibility = View.VISIBLE
            movieYear.visibility = View.VISIBLE
            movieRuntime.visibility = View.VISIBLE
            movieGenre.visibility = View.VISIBLE
            movieImdbRating.visibility = View.VISIBLE
            buttonShare.visibility = View.VISIBLE


            movieLink.apply {
                text = resources.getString(R.string.imdb_page)
                visibility = View.VISIBLE
                setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/${movieResponse.imdbID}/"))
                    startActivity(intent)
                }
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotEmpty()) {
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        }

        /*
        buttonShare listener that allows user to send title/link via installed platforms.

         */
        buttonShare.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${movieTitle.text}! Here's more about it: https://www.imdb.com/title/${viewModel.movieData.value?.imdbID}/")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

    }
}
