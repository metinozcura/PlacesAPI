package com.metinozcura.placesapi

import androidx.databinding.DataBindingUtil
import com.metinozcura.placesapi.di.component.DaggerAppComponent
import com.metinozcura.placesapi.di.component.DaggerBindingComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class PlacesApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this).also { appComponent ->
            appComponent.inject(this@PlacesApp)

            val bindingComponent =
                DaggerBindingComponent.builder().appComponent(appComponent).build()
            DataBindingUtil.setDefaultComponent(bindingComponent)
        }
    }
}