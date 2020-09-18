package com.mohamedsayed.theair.model.objects

import com.google.gson.annotations.SerializedName

data class TvShow (
    @SerializedName("poster_path") val poster_path : String,
    @SerializedName("popularity") val popularity : Double,
    @SerializedName("id") val id : Int,
    @SerializedName("backdrop_path") val backdrop_path : String,
    @SerializedName("vote_average") val vote_average : Double,
    @SerializedName("overview") val overview : String,
    @SerializedName("first_air_date") val first_air_date : String,
    @SerializedName("origin_country") val origin_country : List<String>,
    @SerializedName("genre_ids") val genre_ids : List<Int>,
    @SerializedName("original_language") val original_language : String,
    @SerializedName("vote_count") val vote_count : Int,
    @SerializedName("name") val name : String,
    @SerializedName("original_name") val original_name : String
)