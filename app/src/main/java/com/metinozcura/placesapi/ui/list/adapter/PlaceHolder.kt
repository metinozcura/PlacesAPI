package com.metinozcura.placesapi.ui.list.adapter

import androidx.databinding.library.baseAdapters.BR
import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.base.BaseViewHolder
import com.metinozcura.placesapi.databinding.ItemPlaceBinding
import com.metinozcura.placesapi.helper.ext.setImage
import com.metinozcura.placesapi.model.Place
import kotlinx.android.synthetic.main.item_place.view.*

class PlaceHolder(viewBinding: ItemPlaceBinding) : BaseViewHolder<Place, ItemPlaceBinding>(viewBinding) {
    override fun bindingVariable(): Int {
        return BR.model
    }

    override fun bind() {
        val place = getRowItem()
        val context = itemView.context
        var imageUrl = ""
        if (!place?.photos.isNullOrEmpty()) {
            imageUrl = String.format(
                context.getString(R.string.image_url), 400,
                place!!.photos[0].photoReference, context.getString(R.string.api_key)
            )
        }
        itemView.ivAvatar.setImage(imageUrl)

        val binding = getRowBinding()
        if (place != null && binding != null)
            binding.executePendingBindings()
    }
}