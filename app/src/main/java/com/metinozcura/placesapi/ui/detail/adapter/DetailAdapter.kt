package com.metinozcura.placesapi.ui.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metinozcura.placesapi.databinding.ItemInfoBinding
import com.metinozcura.placesapi.model.DetailModel

class DetailAdapter(private val infoList: MutableList<DetailModel>,
                    private val listener: ActionListener) : RecyclerView.Adapter<DetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder =
        DetailHolder(ItemInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.doBindings(infoList[position])
        holder.bind()
        holder.itemView.setOnClickListener { listener.onAction(infoList[position].icon, infoList[position].text) }
    }

    override fun getItemCount(): Int = infoList.size
}