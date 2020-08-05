package com.metinozcura.placesapi.api

import com.metinozcura.placesapi.model.NearbyResponse
import com.metinozcura.placesapi.model.PlaceDetailResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesApi {
    @GET("place/nearbysearch/json")
    fun getNearbyPlaces(
        @Query("location") location: String?, @Query("radius") radius: Int?,
        @Query("type") type: String?,
        @Query("pagetoken") pageToken: String?, @Query("key") apiKey: String
    ): Flowable<NearbyResponse>

    @GET("place/details/json?")
    fun getPlaceDetail(
        @Query("place_id") placeId: String?, @Query("fields") fields: String?, @Query("key") apiKey: String
    ): Flowable<PlaceDetailResponse>
}