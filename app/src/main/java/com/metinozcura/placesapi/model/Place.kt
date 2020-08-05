package com.metinozcura.placesapi.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

data class Place(
    var id: String,
    var name: String,
    @SerializedName("place_id")
    var placeId: String,
    @SerializedName("business_status")
    var businessStatus: String,
    var rating: Double,
    @SerializedName("user_ratings_total")
    var totalUserRatings: Int,
    var types: List<String>,
    var photos: List<PlacePhoto>,
    var vicinity: String,
    @SerializedName("formatted_address")
    var address: String,
    @SerializedName("formatted_phone_number")
    var phoneNumber: String,
    @SerializedName("plus_code")
    var plusCode: PlusCode,
    var url: String,
    @SerializedName("opening_hours")
    var openingHours: OpeningHours,
    @Transient
    var visited: Boolean
) {
    companion object {
        val placeDiffUtil = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                if (oldItem.id == newItem.id) {
                    return true
                }
                return false
            }

            override fun areContentsTheSame(
                oldItem: Place,
                newItem: Place
            ): Boolean {
                if (oldItem.id == newItem.id) {
                    return true
                }
                return false
            }
        }
    }
}