package com.metinozcura.placesapi.helper.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginDecoration() : RecyclerView.ItemDecoration() {
    private var topHeight: Int = 0
    private var leftHeight: Int = 0
    private var rightHeight: Int = 0
    private var bottomHeight: Int = 0

    constructor(height: Int) : this() {
        this.topHeight = height
        this.leftHeight = height
        this.rightHeight = height
        this.bottomHeight = height
    }

    constructor(topHeight: Int, leftHeight: Int,
                rightHeight: Int, bottomHeight: Int) : this() {
        this.topHeight = topHeight
        this.leftHeight = leftHeight
        this.rightHeight = rightHeight
        this.bottomHeight = bottomHeight
    }

    constructor(verticalHeight: Int, horizontalHeight: Int) : this() {
        this.topHeight = verticalHeight
        this.leftHeight = horizontalHeight
        this.rightHeight = horizontalHeight
        this.bottomHeight = verticalHeight
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        outRect.top = topHeight
        outRect.left = leftHeight
        outRect.right = rightHeight
        outRect.bottom = bottomHeight
    }
}