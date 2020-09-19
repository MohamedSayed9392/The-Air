package com.mohamedsayed.theair.viewmodel

import GuestSession
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService


class MainViewModel(val apiService: ApiService) : ViewModel() {

    var getGuestSessionId: LiveData<ApiResponse<GuestSession>>? = null
    fun getGuestSessionId(): LiveData<ApiResponse<GuestSession>> {
        getGuestSessionId = apiService.getGuestSessionId()
        return getGuestSessionId!!
    }
}