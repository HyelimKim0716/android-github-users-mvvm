package com.githubapi.search.searchgithubusers.ui

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.R
import kotlinx.android.synthetic.main.recycler_view_sticky_header_decoration.view.*


class StickyItemDecoration : RecyclerView.ItemDecoration() {

    lateinit var stickyItemDecorationCallback: StickyItemDecorationCallback
    private lateinit var headerView: View

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        parent?.getChildAdapterPosition(view)?.apply {
            if (stickyItemDecorationCallback.isSection(this)) {
                outRect?.top = 80
                outRect?.bottom = 80
            }
        }
    }

    override fun onDrawOver(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(canvas, parent, state)

        parent ?: return
        parent.getChildAt(0) ?: return

        headerView = inflateHeaderView(parent).apply {
            fixLayoutSize(parent, this)
        }

        var previousHeader: CharSequence = ""

        for (i in 0 until parent.childCount) {

            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)

            val title = stickyItemDecorationCallback.getSectionHeader(position).toString().toUpperCase()
            headerView.stickyHeader_tvHeader?.text = title

            if (previousHeader.toString().toUpperCase() != title
                    || stickyItemDecorationCallback.isSection(position)) {

                canvas ?: return

                drawHeader(canvas, child, headerView)
                previousHeader = title
            }
        }

    }

    private fun drawHeader(canvas: Canvas, child: View, headerView: View) {
        canvas.save()
        canvas.translate(0f, Math.max(0, child.top.minus(headerView.height)).toFloat())
        headerView.draw(canvas)
        canvas.restore()
    }

    private fun drawHeader(canvas: Canvas, header: View) {
        canvas.save()
        canvas.translate(0F, 0F)
        header.draw(canvas)
        canvas.restore()
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View) {
        canvas.save()
        canvas.translate(0F, currentHeader.top.minus(headerView.height).toFloat())
        headerView.draw(canvas)
        canvas.restore()
    }

    private fun inflateHeaderView(parent: RecyclerView): View {
        return LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_sticky_header_decoration,
                        parent,
                        false)
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

}