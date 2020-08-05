package com.metinozcura.placesapi.ui.detail

import androidx.lifecycle.MutableLiveData
import com.metinozcura.placesapi.api.PlacesApi
import com.metinozcura.placesapi.base.BaseViewModel
import com.metinozcura.placesapi.helper.status.ViewState
import com.metinozcura.placesapi.model.PlaceDetailResponse
import javax.inject.Inject

class DetailViewModel @Inject constructor(var placesApi: PlacesApi) : BaseViewModel() {
    var placeDetail: MutableLiveData<ViewState<PlaceDetailResponse>> = MutableLiveData()
    fun getPlaceDetail(placeId: String, fields: String, key: String) {
        placesApi.getPlaceDetail(placeId, fields, key)
            .makeRequest {
                placeDetail.value = it
            }
    }
}