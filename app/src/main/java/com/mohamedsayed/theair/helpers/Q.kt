package com.mohamedsayed.theair.helpers

object Q {

    /*************** Apis **************/
    const val API_TOKEN_V3 = "244e0b88ca3ed893d6b9181861e71988"

    const val BASE_API = "https://api.themoviedb.org/3/"

    const val GET_GUEST_SESSION_ID_API = "authentication/guest_session/new"
    const val GET_LATEST_TV_SHOWS_API = "tv/top_rated"
    const val GET_TV_SHOW_DETAILS_API = "tv/{tv_id}"
    const val RATE_TV_SHOW_API = "tv/{tv_id}/rating"
    const val GET_TV_SHOW_SIMILAR_API = "tv/{tv_id}/similar"

    const val POSTER_URL_PATH = "https://image.tmdb.org/t/p/w500/%s"
}
