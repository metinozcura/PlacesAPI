package com.metinozcura.placesapi.di.builder

import com.metinozcura.placesapi.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}