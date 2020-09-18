package com.mohamedsayed.theair.viewmodel

import TvShowDetails
import TvShowResults
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService
import com.mohamedsayed.theair.model.network.RetrofitService


class TvShowDetailsVModel(val apiService: ApiService) : ViewModel() {

    fun getTvShowDetails(tv_id: Long): LiveData<ApiResponse<TvShowDetails>> {
        return apiService.getTvShowDetails(tv_id)
    }

    fun rateTvShow(tv_id: Long,value:Double): LiveData<ApiResponse<String>> {
        return apiService.rateTvShow(tv_id,value)
    }

    fun getTvShowSimilar(tv_id: Long): LiveData<ApiResponse<TvShowResults>> {
        return apiService.getTvShowSimilar(tv_id)
    }
}