package com.metinozcura.placesapi.api

import java.io.IOException

class NoConnectionException : IOException() {
    override val message: String
        get() = "No Internet Connection"
}