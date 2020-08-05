package com.metinozcura.placesapi.model

import com.google.gson.annotations.SerializedName

data class PlacePhoto(
    var width: Int,
    var height: Int,
    @SerializedName("photo_reference")
    var photoReference: String
)