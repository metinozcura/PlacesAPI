package com.metinozcura.placesapi.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.NonNull
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.metinozcura.placesapi.helper.SharedPrefHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilityModule {
    @Provides
    @Singleton
    internal fun provideGson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .setLenient()
            .create()

    @Singleton
    @Provides
    internal fun provideSharedPreference(@NonNull context: Context): SharedPreferences =
        context.getSharedPreferences("PlacesDemo", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    internal fun provideSharedHelper(sharedPreferences: SharedPreferences, gson: Gson): SharedPrefHelper =
        SharedPrefHelper(sharedPreferences, gson)
}