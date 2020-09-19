package com.mohamedsayed.theair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService
import com.mohamedsayed.theair.model.objects.DefaultResponse
import com.mohamedsayed.theair.model.objects.TvShowDetails
import com.mohamedsayed.theair.model.objects.TvShowResults


class TvShowDetailsVModel(val apiService: ApiService) : ViewModel() {

    var getTvShowDetails: LiveData<ApiResponse<TvShowDetails>>? = null
    fun getTvShowDetails(tv_id: Int): LiveData<ApiResponse<TvShowDetails>> {
        getTvShowDetails = apiService.getTvShowDetails(tv_id)
        return getTvShowDetails!!
    }

    var rateTvShow: LiveData<ApiResponse<DefaultResponse>>? = null
    fun rateTvShow(tv_id: Int, value: Double,guest_session_id:String): LiveData<ApiResponse<DefaultResponse>> {
        val body = HashMap<String,Any>()
        body["value"] = value
        rateTvShow = apiService.rateTvShow(tv_id,body, guest_session_id)
        return rateTvShow!!
    }

    var getTvShowSimilar: LiveData<ApiResponse<TvShowResults>>? = null
    fun getTvShowSimilar(tv_id: Int): LiveData<ApiResponse<TvShowResults>> {
        getTvShowSimilar = apiService.getTvShowSimilar(tv_id)
        return getTvShowSimilar!!
    }
}