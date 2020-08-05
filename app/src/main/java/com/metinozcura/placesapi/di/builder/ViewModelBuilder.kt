package com.metinozcura.placesapi.di.builder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.metinozcura.placesapi.di.vmDi.ApplicationViewModelFactory
import com.metinozcura.placesapi.di.vmDi.ViewModelKey
import com.metinozcura.placesapi.ui.detail.DetailViewModel
import com.metinozcura.placesapi.ui.list.PlaceListViewModel
import com.metinozcura.placesapi.ui.main.MainViewModel
import com.metinozcura.placesapi.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBuilder {
    @Binds
    internal abstract fun bindsApplicationModelFactory(viewModelFactory: ApplicationViewModelFactory):
            ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindsSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaceListViewModel::class)
    internal abstract fun bindsPlaceListViewModel(viewModel: PlaceListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    internal abstract fun bindsDetailViewModel(viewModel: DetailViewModel): ViewModel
}