package com.mohamedsayed.theair.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService
import com.mohamedsayed.theair.model.objects.TvShowResults


class LatestViewModel(val apiService: ApiService) : ViewModel() {

    var getLatestTvShows: LiveData<ApiResponse<TvShowResults>>? = null
    fun getLatestTvShows(): LiveData<ApiResponse<TvShowResults>> {
        getLatestTvShows = apiService.getLatestTvShows(1)
        return getLatestTvShows!!
    }
}