package com.metinozcura.placesapi.di.builder

import com.metinozcura.placesapi.ui.detail.DetailFragment
import com.metinozcura.placesapi.ui.list.PlaceListFragment
import com.metinozcura.placesapi.ui.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector
    internal abstract fun contributeSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    internal abstract fun contributePlaceListFragment(): PlaceListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeDetailFragment(): DetailFragment
}