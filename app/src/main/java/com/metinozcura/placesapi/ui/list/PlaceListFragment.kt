package com.metinozcura.placesapi.ui.list

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.base.BaseFragment
import com.metinozcura.placesapi.databinding.FragmentListBinding
import com.metinozcura.placesapi.helper.decoration.MarginDecoration
import com.metinozcura.placesapi.helper.status.ViewStatus
import com.metinozcura.placesapi.ui.list.adapter.ItemClickListener
import com.metinozcura.placesapi.ui.list.adapter.PlaceListAdapter

class PlaceListFragment : BaseFragment<FragmentListBinding, PlaceListViewModel>(), ItemClickListener {
    private var latLng: String? = null
    private lateinit var placeListAdapter: PlaceListAdapter
    override val layoutId: Int
        get() = R.layout.fragment_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        latLng = arguments?.getString("latLng")
        if (!latLng.isNullOrEmpty())
            viewModel.getPlaces(latLng!!, 1000, "restaurant", getString(R.string.api_key))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.infoView.retry = { viewModel.retry() }
        viewModel.placePagedList.observe(viewLifecycleOwner, Observer {
            placeListAdapter.submitList(it)
            binding.rvPlaces.scheduleLayoutAnimation()
        })

        viewModel.initialLoadingStatus.observe(viewLifecycleOwner, Observer {
            binding.infoView.setState(it.status, it.message)
            if (it.status == ViewStatus.SUCCESS)
                binding.rvPlaces.visibility = View.VISIBLE
        })

        viewModel.nextPageLoadingStatus.observe(viewLifecycleOwner, Observer {
            placeListAdapter.setNetworkState(it)
        })

        initAdapter()
    }

    override fun onItemClicked(placeId: String?, placeName: String?) {
        if (placeId.isNullOrEmpty()) return

        viewModel.addVisitedPlace(placeId)
        val bundle = bundleOf("placeId" to placeId, "placeName" to placeName, "latLng" to latLng)
        findNavController().navigate(R.id.action_placeList_to_detail, bundle)
    }

    private fun initAdapter() {
        placeListAdapter = PlaceListAdapter(this, viewModel.visitedPlaces) { viewModel.retry() }
        binding.rvPlaces.adapter = placeListAdapter
        binding.rvPlaces.addItemDecoration(MarginDecoration(resources.getDimension(R.dimen.list_vertical_margin).toInt(), resources.getDimension(R.dimen.list_horizontal_margin).toInt()))
    }
}