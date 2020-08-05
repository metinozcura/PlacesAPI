package com.metinozcura.placesapi.ui.list.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.metinozcura.placesapi.api.PlacesApi
import com.metinozcura.placesapi.helper.ext.addTo
import com.metinozcura.placesapi.helper.status.ViewState
import com.metinozcura.placesapi.helper.status.ViewStatus
import com.metinozcura.placesapi.model.Place
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class PlacePagingDataSource constructor(
    private var placesApi: PlacesApi,
    private var compositeDisposable: CompositeDisposable,
    private var initialLoadingStatus: MutableLiveData<ViewState<ViewStatus>>,
    private var nextPageLoadingStatus: MutableLiveData<ViewState<ViewStatus>>,
    private var location: String,
    private var radius: Int,
    private var type: String,
    private var apiKey: String
) : PageKeyedDataSource<Int, Place>() {
    var currentPage = 1
    var nextPage = 2
    private var retryCompletable: Completable? = null
    private var nextPageToken: String? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Place>) {
        initialLoadingStatus.postValue(ViewState.loading())
        placesApi.getNearbyPlaces(location, radius, type, nextPageToken, apiKey)
            .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe({
                nextPageToken = it.nextPageToken
                when (it.status) {
                    "OK" -> initialLoadingStatus.postValue(ViewState.success())
                    "ZERO_RESULTS" -> setError(initialLoadingStatus, "No results.")
                    else -> setError(initialLoadingStatus, it.status)
                }
                callback.onResult(it.results, null, nextPage)
                setRetry(null)
            }, {
                setError(initialLoadingStatus, it.message)
                setRetry(Action { loadInitial(params, callback) })
            }).addTo(compositeDisposable)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Place>) {
        if (nextPageToken.isNullOrEmpty())
            return

        nextPageLoadingStatus.postValue(ViewState.loading())
        placesApi.getNearbyPlaces(location, radius, type, nextPageToken, apiKey)
            .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
            .subscribe({
                nextPageToken = it.nextPageToken
                when (it.status) {
                    "OK" -> nextPageLoadingStatus.postValue(ViewState.success())
                    "ZERO_RESULTS" -> setError(nextPageLoadingStatus, "No more results.")
                    else -> setError(nextPageLoadingStatus, it.status)
                }
                nextPageLoadingStatus.postValue(ViewState.success())
                callback.onResult(it.results, params.key + 1)
                setRetry(null)
            }, {
                setError(nextPageLoadingStatus, it.message)
                setRetry(Action { loadAfter(params, callback) })
            }).addTo(compositeDisposable)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Place>) = Unit

    private fun setError(loadingStatus: MutableLiveData<ViewState<ViewStatus>>, errorMsg: String?) {
        if (errorMsg.isNullOrEmpty())
            loadingStatus.postValue(ViewState.error("An error occured."))
        else
            loadingStatus.postValue(ViewState.error(errorMsg))
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}