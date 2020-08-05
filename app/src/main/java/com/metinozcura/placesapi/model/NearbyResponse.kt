package com.metinozcura.placesapi.model

import com.google.gson.annotations.SerializedName

data class NearbyResponse(
    @SerializedName("next_page_token")
    val nextPageToken: String,
    val results: List<Place>,
    val status: String
)
