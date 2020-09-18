package com.mohamedsayed.theair.model.network

import TvShowDetails
import TvShowResults
import androidx.lifecycle.LiveData
import com.mohamedsayed.theair.helpers.Q
import retrofit2.http.*

interface ApiService {
    @GET(Q.GET_LATEST_TV_SHOWS_API)
    fun getLatestTvShows(@Query("page") page:Int): LiveData<ApiResponse<TvShowResults>>

    @GET(Q.GET_TV_SHOW_DETAILS_API)
    fun getTvShowDetails(@Path("tv_id") tv_id: Long): LiveData<ApiResponse<TvShowDetails>>

    @GET(Q.GET_TV_SHOW_SIMILAR_API)
    fun getTvShowSimilar(@Path("tv_id") tv_id: Long): LiveData<ApiResponse<TvShowResults>>

    @POST(Q.RATE_TV_SHOW_API)
    fun rateTvShow(
        @Path("tv_id") tv_id: Long,
        @Body value: Double
    ): LiveData<ApiResponse<String>>
}