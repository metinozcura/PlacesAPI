package com.metinozcura.placesapi.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.gson.reflect.TypeToken
import com.metinozcura.placesapi.api.PlacesApi
import com.metinozcura.placesapi.base.BaseViewModel
import com.metinozcura.placesapi.helper.SharedPrefHelper
import com.metinozcura.placesapi.helper.status.ViewState
import com.metinozcura.placesapi.helper.status.ViewStatus
import com.metinozcura.placesapi.model.Place
import com.metinozcura.placesapi.ui.list.paging.PlacePagingFactory
import javax.inject.Inject

class PlaceListViewModel @Inject constructor(
    var placesApi: PlacesApi, var sharedPrefHelper: SharedPrefHelper) : BaseViewModel() {
    lateinit var placePagedList: LiveData<PagedList<Place>>
    var initialLoadingStatus = MutableLiveData<ViewState<ViewStatus>>()
    var nextPageLoadingStatus = MutableLiveData<ViewState<ViewStatus>>()
    var visitedPlaces = MutableLiveData<MutableList<String>>()
    private lateinit var placePagingFactory: PlacePagingFactory

    init {
        val itemType = object : TypeToken<MutableList<String>>() {}
        visitedPlaces.value = sharedPrefHelper.retrieveListData("visited", itemType)
    }

    fun getPlaces(location: String, radius: Int, type: String, apiKey: String) {
        placePagingFactory = PlacePagingFactory(
            placesApi, compositeDisposable, initialLoadingStatus,
            nextPageLoadingStatus, location, radius, type, apiKey)
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setPrefetchDistance(1)
            .setEnablePlaceholders(false)
            .build()
        placePagedList = LivePagedListBuilder<Int, Place>(placePagingFactory, config).build()
    }

    fun retry() {
        placePagingFactory.liveDataSource.value?.retry()
    }

    fun addVisitedPlace(placeId: String) {
        sharedPrefHelper.appendListData("visited", placeId)
        visitedPlaces.value?.add(placeId)
    }
}