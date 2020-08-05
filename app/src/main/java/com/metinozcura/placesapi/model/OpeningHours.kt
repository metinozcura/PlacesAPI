package com.metinozcura.placesapi.model

import com.google.gson.annotations.SerializedName

data class OpeningHours(
    @SerializedName("open_now")
    var openNow: Boolean
)