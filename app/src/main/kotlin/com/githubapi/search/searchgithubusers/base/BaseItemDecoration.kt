package com.githubapi.search.searchgithubusers.base

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.ui.StickyHeaderInterface
import com.githubapi.search.searchgithubusers.ui.StickyHeaderViewEvent
import io.reactivex.subjects.PublishSubject

class BaseItemDecoration: RecyclerView.ItemDecoration() {

    val itemDecorationEventSender = PublishSubject.create<Pair<StickyHeaderViewEvent, Any>>()

    lateinit var stickyHeaderListener: StickyHeaderInterface

    override fun onDrawOver(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(canvas, parent, state)

        val topChild = parent?.getChildAt(0)
        topChild ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) return

        val currentHeader = getHeaderViewForItem(topChildPosition, parent)
        currentHeader?.let { fixLayoutSize(parent, it) }
        val contactPoint = currentHeader?.bottom
        val childInContact = contactPoint?.let { getChildInContact(parent, it) }

        childInContact?: return

        if (stickyHeaderListener.isHeader(parent.getChildAdapterPosition(childInContact))) {
            canvas?.let { moveHeader(it, currentHeader, childInContact) }
            return
        }

        canvas?.let { drawHeader(it, currentHeader) }

    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View? {
        var header: View ?= null
        stickyHeaderListener?.let { listener ->
            val headerPosition = listener.getHeaderPositionForItem(itemPosition)
            val layoutResId = listener.getHeaderLayout(headerPosition)

            header = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false).apply {
                listener.bindHeaderData(this, headerPosition)
            }
        }

        return header
    }


    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft.plus(parent.paddingRight), view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop.plus(parent.paddingBottom), view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)


        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {

        var childInContact: View ?= null

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)

            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    childInContact = child
                    return childInContact

                }
            }
        }

        return null
    }

    private fun drawHeader(canvas: Canvas, header: View) {
        canvas.save()
        canvas.translate(0F, 0F)
        header.draw(canvas)
        canvas.restore()
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View, nextHeader: View) {
        canvas.save()
        canvas.translate(0F, nextHeader.top.minus(currentHeader.height).toFloat())
        currentHeader.draw(canvas)
        canvas.restore()
    }

}