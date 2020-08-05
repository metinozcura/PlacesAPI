package com.metinozcura.placesapi.di.component

import android.app.Application
import com.metinozcura.placesapi.PlacesApp
import com.metinozcura.placesapi.di.builder.ActivityBuilder
import com.metinozcura.placesapi.di.builder.FragmentBuilder
import com.metinozcura.placesapi.di.builder.ViewModelBuilder
import com.metinozcura.placesapi.di.module.ContextModule
import com.metinozcura.placesapi.di.module.NetworkModule
import com.metinozcura.placesapi.di.module.UtilityModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityBuilder::class,
        ViewModelBuilder::class,
        FragmentBuilder::class,
        NetworkModule::class,
        ContextModule::class,
        UtilityModule::class
    ]
)

interface AppComponent : AndroidInjector<PlacesApp> {
    override fun inject(instance: PlacesApp?)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}