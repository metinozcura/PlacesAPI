package com.metinozcura.placesapi.helper.status

data class ViewState<out T>(val status: ViewStatus, val data: T?, val message: String? = null) {
    companion object {
        fun <T> success(data: T? = null): ViewState<T> = ViewState(ViewStatus.SUCCESS, data, null)

        fun <T> error(msg: String): ViewState<T> = ViewState(ViewStatus.ERROR, null, msg)

        fun <T> loading(data: T? = null): ViewState<T> = ViewState(ViewStatus.LOADING, data, null)
    }
}