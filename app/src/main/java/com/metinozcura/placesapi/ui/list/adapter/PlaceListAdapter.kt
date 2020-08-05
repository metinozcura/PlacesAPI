package com.metinozcura.placesapi.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.metinozcura.placesapi.databinding.ItemPlaceBinding
import com.metinozcura.placesapi.helper.status.ViewState
import com.metinozcura.placesapi.helper.status.ViewStatus
import com.metinozcura.placesapi.model.Place

class PlaceListAdapter(
    private val listener: ItemClickListener,
    private val visitedPlaces: MutableLiveData<MutableList<String>>,
    private val retry: () -> Unit
) :
    PagedListAdapter<Place, RecyclerView.ViewHolder>(Place.placeDiffUtil) {

    private val DATA_VIEW_TYPE = 1
    private val FOOTER_VIEW_TYPE = 2
    private var viewState: ViewState<ViewStatus>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE)
            PlaceHolder(ItemPlaceBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
        else
            LoadingHolder.create(retry, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        if (getItemViewType(position) == DATA_VIEW_TYPE) {
            holder as PlaceHolder
            val place = getItem(position)
            place?.visited = visitedPlaces.value?.contains(place?.placeId)!!
            holder.doBindings(place)
            holder.bind()

            holder.itemView.setOnClickListener {
                listener.onItemClicked(getItem(position)?.placeId, getItem(position)?.name)
            }
        } else {
            (holder as LoadingHolder).bind(viewState)
        }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1)
            FOOTER_VIEW_TYPE
        else
            DATA_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    /**
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: ViewState<ViewStatus>?) {
        if (currentList != null && currentList!!.size != 0) {
            val previousState = this.viewState
            val hadExtraRow = hasExtraRow()
            this.viewState = newNetworkState
            val hasExtraRow = hasExtraRow()
            if (hadExtraRow != hasExtraRow) {
                if (hadExtraRow)
                    notifyItemRemoved(super.getItemCount())
                else
                    notifyItemInserted(super.getItemCount())

            } else if (hasExtraRow && previousState !== newNetworkState) {
                notifyItemChanged(itemCount - 1)
            }
        }
    }

    private fun hasExtraRow(): Boolean {
        return viewState != null && viewState!!.status != ViewStatus.SUCCESS
    }
}