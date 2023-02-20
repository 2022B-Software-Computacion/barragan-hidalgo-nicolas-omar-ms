package com.example.recycler_nb_app.ui.movies
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MovieViewModel (

):ViewModel(){
    private val _catalogList : MutableLiveData<List<MovieCatalog>> = MutableLiveData()
    val catalogList : LiveData<List<MovieCatalog>>
        get() = _catalogList

    fun getMoviesCatalog() {
        viewModelScope.launch {
            _catalogList.value = MoviesProvider.movieCatalog
        }
    }

}