package com.tuankhaiit.androidfeatureslibrary.presentation.common.adapter

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.search.SearchBar
import com.tuankhaiit.androidfeatureslibrary.R
import kotlin.properties.Delegates

class StickyHeaderItemDecoration(
    private val callback: StickyHeaderItemCallback
) : RecyclerView.ItemDecoration() {
    fun attachToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addItemDecoration(this)
    }

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        if (callback.itemCount() == 0) {
            return
        }

        val layoutManager = parent.layoutManager
        val topChildPosition = if (layoutManager is LinearLayoutManager) {
            layoutManager.findFirstVisibleItemPosition()
        } else {
            parent.getChildAt(0)?.let { topChild ->
                parent.getChildAdapterPosition(topChild)
            } ?: RecyclerView.NO_POSITION
        }

        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val currentHeader: View =
            callback.getHeaderLayoutView(parent, topChildPosition)
                ?: return

        fixLayoutSize(parent, currentHeader)

        val viewOverlappedByHeader: View =
            findViewOverlappedByHeader(parent, currentHeader.bottom) ?: return

        val overlappedByHeaderPosition = parent.getChildAdapterPosition(viewOverlappedByHeader)
        if (overlappedByHeaderPosition == RecyclerView.NO_POSITION) return

        when {
            callback.isHeader(overlappedByHeaderPosition) ->
                moveHeader(canvas, currentHeader, viewOverlappedByHeader)
            else ->
                drawHeader(canvas, currentHeader)
        }
    }

    private fun findViewOverlappedByHeader(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.top <= contactPoint && child.bottom > contactPoint) {
                childInContact = child
                break
            }
        }
        return childInContact
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View, nextHeader: View) {
        canvas.save()
        canvas.translate(0f, nextHeader.top - currentHeader.height.toFloat())
        currentHeader.draw(canvas)
        canvas.restore()
    }

    private fun drawHeader(canvas: Canvas, header: View) {
        canvas.save()
        canvas.translate(0f, 0f)
        header.draw(canvas)
        canvas.restore()
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        val widthSpec = View.MeasureSpec.makeMeasureSpec(
            parent.width,
            View.MeasureSpec.EXACTLY
        )
        val heightSpec = View.MeasureSpec.makeMeasureSpec(
            parent.height,
            View.MeasureSpec.EXACTLY
        )
        val childWidth: Int = ViewGroup.getChildMeasureSpec(
            widthSpec,
            parent.paddingLeft + parent.paddingRight,
            view.layoutParams.width
        )
        val childHeight: Int = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidth, childHeight)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    interface StickyHeaderItemCallback {
        fun itemCount(): Int
        fun isHeader(position: Int): Boolean
        fun getHeaderLayoutView(parent: RecyclerView, position: Int): View?
    }
}