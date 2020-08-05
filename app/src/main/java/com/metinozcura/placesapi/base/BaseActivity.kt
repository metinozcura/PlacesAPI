package com.metinozcura.placesapi.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.libraries.places.api.Places
import com.metinozcura.placesapi.R
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<DB : ViewDataBinding, VM : BaseViewModel> : DaggerAppCompatActivity() {
    lateinit var binding: DB
    lateinit var viewModel: VM

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @get:LayoutRes
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getTClass())
        if (!Places.isInitialized())
            Places.initialize(applicationContext, getString(R.string.api_key))
    }

    private fun getTClass(): Class<VM> =
        (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
}