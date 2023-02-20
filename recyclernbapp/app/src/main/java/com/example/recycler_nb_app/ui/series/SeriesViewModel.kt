package com.example.recycler_nb_app.ui.series
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class SeriesViewModel (

):ViewModel(){
    private val _catalogList : MutableLiveData<List<SeriesCatalog>> = MutableLiveData()
    val catalogList : LiveData<List<SeriesCatalog>>
        get() = _catalogList

    fun getSeriesCatalog() {
        viewModelScope.launch {
            _catalogList.value = SeriesProvider.seriesCatalog
        }
    }

}