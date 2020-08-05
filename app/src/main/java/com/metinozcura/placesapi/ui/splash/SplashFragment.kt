package com.metinozcura.placesapi.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Handler
import android.provider.Settings
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.androidisland.ezpermission.EzPermission
import com.google.android.gms.location.*
import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.base.BaseFragment
import com.metinozcura.placesapi.databinding.FragmentSplashBinding
import com.metinozcura.placesapi.helper.ext.showDialog
import java.util.*

class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    override val layoutId: Int
        get() = R.layout.fragment_splash

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    @SuppressLint("MissingPermission")
    private fun getLatLng() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 20 * 1000

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (location != null) {
                        fusedLocationProviderClient.removeLocationUpdates(this)
                        Handler().postDelayed({
                            val latLng = String.format(Locale.US, "%s,%s", location.latitude, location.longitude)
                            val bundle = bundleOf("latLng" to latLng)
                            findNavController().navigate(R.id.action_splash_to_placeList, bundle)
                        }, 1500L)
                    }
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun checkPermissions() {
        EzPermission.with(requireContext())
            .permissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .request { granted, denied, _ ->
                when {
                    granted.contains(Manifest.permission.ACCESS_FINE_LOCATION) -> checkGpsStatus()
                    denied.contains(Manifest.permission.ACCESS_FINE_LOCATION) -> checkPermissions()
                    else -> showPermissionDialog()
                }
            }
    }

    private fun checkGpsStatus() {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (gpsStatus) getLatLng()
        else showGpsDialog()
    }

    private fun showPermissionDialog() {
        showDialog(
            R.string.permission_dialog_text,
            positiveAction = {
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + activity?.packageName)))
            },
            negativeAction = { activity?.finish() })
    }

    private fun showGpsDialog() {
        showDialog(
            R.string.activate_gps,
            positiveAction = { startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) },
            negativeAction = { activity?.finish() })
    }
}