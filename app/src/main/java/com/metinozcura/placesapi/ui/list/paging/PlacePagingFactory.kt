package com.metinozcura.placesapi.ui.list.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.metinozcura.placesapi.api.PlacesApi
import com.metinozcura.placesapi.helper.status.ViewState
import com.metinozcura.placesapi.helper.status.ViewStatus
import com.metinozcura.placesapi.model.Place
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PlacePagingFactory @Inject constructor(
    private var placesApi: PlacesApi,
    var compositeDisposable: CompositeDisposable,
    var initialLoadingStatus: MutableLiveData<ViewState<ViewStatus>>,
    var nextPageLoadingStatus: MutableLiveData<ViewState<ViewStatus>>,
    var location: String, var radius: Int, var type: String, var apiKey: String
) : DataSource.Factory<Int, Place>() {

    var liveDataSource = MutableLiveData<PlacePagingDataSource>()
    var pageIndex = 1

    override fun create(): DataSource<Int, Place> {
        val dataSource = PlacePagingDataSource(placesApi, compositeDisposable, initialLoadingStatus,
            nextPageLoadingStatus, location, radius, type, apiKey)
        dataSource.currentPage = pageIndex
        liveDataSource.postValue(dataSource)
        return dataSource
    }
}