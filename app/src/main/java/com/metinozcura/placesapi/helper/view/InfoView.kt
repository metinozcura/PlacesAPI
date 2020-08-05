package com.metinozcura.placesapi.helper.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.metinozcura.placesapi.R
import com.metinozcura.placesapi.helper.status.ViewStatus
import kotlinx.android.synthetic.main.custom_info_view.view.*

class InfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    lateinit var retry: () -> Unit

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_info_view, this, true)
    }

    fun setState(it: ViewStatus, message: String?) {
        when (it) {
            ViewStatus.ERROR -> {
                tvError.visibility = View.VISIBLE
                tvError.text =
                    String.format(context.getString(R.string.generic_error_message, message))
                indicatorLoading.visibility = View.GONE
                infoRoot.setOnClickListener { callRetry() }
            }
            ViewStatus.LOADING -> {
                tvError.visibility = View.GONE
                indicatorLoading.visibility = View.VISIBLE
            }
            ViewStatus.SUCCESS -> {
                infoRoot.visibility = View.GONE
            }
        }
    }

    private fun callRetry() {
        retry.invoke()
    }
}