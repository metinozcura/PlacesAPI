package com.metinozcura.placesapi.ui.detail.adapter

import androidx.databinding.library.baseAdapters.BR
import com.bumptech.glide.Glide
import com.metinozcura.placesapi.base.BaseViewHolder
import com.metinozcura.placesapi.databinding.ItemInfoBinding
import com.metinozcura.placesapi.model.DetailModel

class DetailHolder(viewBinding: ItemInfoBinding) :
    BaseViewHolder<DetailModel, ItemInfoBinding>(viewBinding) {
    override fun bindingVariable(): Int {
        return BR.model
    }

    override fun bind() {
        val info = getRowItem()
        val binding = getRowBinding()
        if (info != null && binding != null) {
            Glide.with(itemView.context).load(info.icon).into(binding.ivInfo)
            binding.executePendingBindings()
        }
    }
}