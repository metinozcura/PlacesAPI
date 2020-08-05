package com.metinozcura.placesapi.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.metinozcura.placesapi.helper.ext.addTo
import com.metinozcura.placesapi.helper.status.ViewState
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
        Log.d("onDestinationChanged: ", "VM cleared")
        super.onCleared()
    }

    fun <T> Flowable<T>.makeRequest(
        successAction: (ViewState<T>) -> Unit
    ) {
        this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { successAction(ViewState.loading()) }
            .subscribe({
                successAction(ViewState.success(it))
            }, {
                if (it.message == null) {
                    successAction(ViewState.error("Hata"))
                } else {
                    successAction(ViewState.error(it.message!!))
                }
            }).addTo(compositeDisposable)
    }
}