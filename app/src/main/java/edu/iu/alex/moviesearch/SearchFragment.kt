package edu.iu.alex.moviesearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var viewModel: ViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextMovie = view.findViewById<EditText>(R.id.editTextMovie)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearch)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        viewModel.init(RetroFitClient.retrofitInstance)


        //Listener for the search action.
        buttonSearch.setOnClickListener{
            val movieTitle = editTextMovie.text.toString().trim()

            if (movieTitle.isNotEmpty()) {
                viewModel.fetchMovieDetails(movieTitle)
            } else {
                editTextMovie.error = "Please enter a movie title"
            }
        }

    }


}