package com.metinozcura.placesapi.ui.main

import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.base.BaseActivity
import com.metinozcura.placesapi.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutId: Int
        get() = R.layout.activity_main
}