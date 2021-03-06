package com.mohamedsayed.theair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedsayed.theair.model.network.ApiResponse
import com.mohamedsayed.theair.model.network.ApiService
import com.mohamedsayed.theair.model.objects.GuestSession


class MainViewModel(val apiService: ApiService) : ViewModel() {

    var getGuestSessionId: LiveData<ApiResponse<GuestSession>>? = null
    fun getGuestSessionId(): LiveData<ApiResponse<GuestSession>> {
        getGuestSessionId = apiService.getGuestSessionId()
        return getGuestSessionId!!
    }
}