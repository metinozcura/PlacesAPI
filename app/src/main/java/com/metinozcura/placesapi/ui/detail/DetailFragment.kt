package com.metinozcura.placesapi.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.base.BaseFragment
import com.metinozcura.placesapi.databinding.FragmentDetailBinding
import com.metinozcura.placesapi.helper.decoration.MarginDecoration
import com.metinozcura.placesapi.helper.status.ViewStatus
import com.metinozcura.placesapi.model.DetailModel
import com.metinozcura.placesapi.model.Place
import com.metinozcura.placesapi.ui.detail.adapter.ActionListener
import com.metinozcura.placesapi.ui.detail.adapter.DetailAdapter

class DetailFragment : BaseFragment<FragmentDetailBinding, DetailViewModel>(), ActionListener {
    private var placeId: String? = null
    private var placeName: String? = null
    private var latLng: String? = null
    private var place: Place? = null

    override val layoutId: Int
        get() = R.layout.fragment_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        placeId = arguments?.getString("placeId")
        placeName = arguments?.getString("placeName")
        latLng = arguments?.getString("latLng")

        requireActivity().actionBar?.title = placeName
        if (placeId != null)
            viewModel.getPlaceDetail(placeId!!, getString(R.string.detail_fields), getString(R.string.api_key))

        binding.infoView.retry = { viewModel.getPlaceDetail(placeId!!, getString(R.string.detail_fields), getString(R.string.api_key)) }
        viewModel.placeDetail.observe(viewLifecycleOwner, Observer {
            binding.infoView.setState(it.status, it.message)
            if (it.status == ViewStatus.SUCCESS)
                binding.rvDetail.visibility = View.VISIBLE
            if (it.data != null)
                initDetails(it.data.result)
        })
    }

    private fun initDetails(place: Place) {
        this.place = place
        val infoList: MutableList<DetailModel> = mutableListOf()
        infoList.add(DetailModel(R.drawable.ic_directions_white_24dp, place.address))
        infoList.add(DetailModel(R.drawable.ic_phone_white_24dp, place.phoneNumber))
        infoList.add(DetailModel(R.drawable.ic_stars_white_24dp, place.rating.toString()))
        infoList.add(DetailModel(R.drawable.ic_qr_code_black_24dp, place.plusCode.compoundCode))
        val detailAdapter = DetailAdapter(infoList, this)
        binding.rvDetail.adapter = detailAdapter
        binding.rvDetail.addItemDecoration(MarginDecoration(resources.getDimension(R.dimen.list_vertical_margin).toInt(), resources.getDimension(R.dimen.list_horizontal_margin).toInt()))
        binding.rvDetail.scheduleLayoutAnimation()
    }

    override fun onAction(resId: Int, data: String) {
        when (resId) {
            R.drawable.ic_directions_white_24dp -> {
                val gmmIntentUri = Uri.parse("google.navigation:q=".plus(latLng))
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                mapIntent.resolveActivity(requireContext().packageManager)?.let {startActivity(mapIntent)}
            }
            R.drawable.ic_phone_white_24dp -> {
                val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:".plus(place?.phoneNumber)))
                requireContext().startActivity(intentDial)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.delete_dialog_text))
                    .setPositiveButton(R.string.delete) { dialog, _ -> dialog.dismiss() }
                    .setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}