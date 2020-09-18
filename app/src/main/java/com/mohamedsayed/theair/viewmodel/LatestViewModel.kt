package com.mohamedsayed.theair.viewmodel

import TvShowResults
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService


class LatestViewModel(val apiService: ApiService) : ViewModel() {

    fun getLatestTvShows(): LiveData<ApiResponse<TvShowResults>> {
        return apiService.getLatestTvShows(1)
    }
}