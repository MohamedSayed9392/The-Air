package com.mohamedsayed.theair.model.objects

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favourite")
data class TvShow(
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") val poster_path: String,
    @Ignore @SerializedName("popularity") val popularity: Double? = 0.0,
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: Int,
    @Ignore @SerializedName("backdrop_path") val backdrop_path: String? = "",
    @ColumnInfo(name = "vote_average") @SerializedName("vote_average") val vote_average: Double,
    @Ignore @SerializedName("overview") val overview: String? = "",
    @ColumnInfo(name = "first_air_date") @SerializedName("first_air_date") val first_air_date: String,
    @Ignore @SerializedName("origin_country") val origin_country: List<String>? = ArrayList(),
    @Ignore @SerializedName("genre_ids") val genre_ids: List<Int>,
    @Ignore @SerializedName("original_language") val original_language: String? = "",
    @Ignore @SerializedName("vote_count") val vote_count: Int? = 0,
    @ColumnInfo(name = "name") @SerializedName("name") val name: String,
    @Ignore @SerializedName("original_name") val original_name: String? = "",
    @Ignore var isFavourite : Boolean = false
) : Parcelable {
    constructor(
        poster_path: String,
        id: Int,
        vote_average: Double,
        first_air_date: String,
        name: String
    ) :
            this(
                poster_path,
                0.0,
                id,
                "",
                vote_average,
                "",
                first_air_date,
                ArrayList(),
                ArrayList(),
                "",
                0,
                name,
                "",
                false
            )
}