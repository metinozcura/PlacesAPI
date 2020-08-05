package com.metinozcura.placesapi.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.helper.status.ViewState
import com.metinozcura.placesapi.helper.status.ViewStatus
import kotlinx.android.synthetic.main.item_loading.view.*

class LoadingHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(status: ViewState<ViewStatus>?) {
        itemView.progressBar.visibility =
            if (status?.status == ViewStatus.LOADING) View.VISIBLE else View.INVISIBLE
        itemView.tvError.visibility =
            if (status?.status == ViewStatus.ERROR) View.VISIBLE else View.INVISIBLE

        when (status?.status) {
            ViewStatus.LOADING -> {
                itemView.tvError.visibility = View.INVISIBLE
                itemView.progressBar.visibility = View.VISIBLE
            }
            ViewStatus.ERROR -> {
                itemView.tvError.visibility = View.VISIBLE
                itemView.progressBar.visibility = View.INVISIBLE
                if (!status.message.isNullOrEmpty())
                    itemView.tvError.text = status.message
            }
            else -> {
                itemView.tvError.visibility = View.GONE
                itemView.progressBar.visibility = View.GONE
            }
        }
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): LoadingHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            view.tvError.setOnClickListener { retry() }
            return LoadingHolder(view)
        }
    }
}