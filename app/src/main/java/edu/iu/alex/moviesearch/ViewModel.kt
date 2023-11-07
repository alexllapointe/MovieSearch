package edu.iu.alex.moviesearch


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewModel() : ViewModel(){
    private val _movieData = MutableLiveData<MovieResponse>()
    private val _errorMessage = MutableLiveData<String>()

    val movieData: LiveData<MovieResponse> get() = _movieData
    val errorMessage: LiveData<String> get() = _errorMessage
    private lateinit var apiService: OmdbApiService

    fun init(apiService: OmdbApiService) {
        this.apiService = apiService
    }

            /**
            Method used to gather the required movie json data. Once acquired, the data is sent via the view model
            to the result fragment.
             *
             *
             * @param title String
             *
            */

    fun fetchMovieDetails(title: String) {
        apiService.searchMovie(title, "ed4fd6c1").enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    Log.d("Search Fragment", "Got data: ${response.body()}}")
                    _movieData.value = response.body()
                } else {
                    Log.d("Search Fragment", "Invalid Movie.")
                    _errorMessage.value = "Movie Not Found"
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e("Search Fragment", "Error fetching movie details", t)
                _errorMessage.postValue(t.message ?: "Unknown error occurred")
            }
        })
    }
}