package com.metinozcura.placesapi.di.component

import androidx.databinding.DataBindingComponent
import com.metinozcura.placesapi.di.scope.BindingScope
import dagger.Component

@BindingScope
@Component(dependencies = [AppComponent::class])
interface BindingComponent : DataBindingComponent {
}