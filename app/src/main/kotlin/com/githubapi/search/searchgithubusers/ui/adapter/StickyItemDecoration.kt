package com.githubapi.search.searchgithubusers.ui.adapter

import android.graphics.Canvas
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import com.githubapi.search.searchgithubusers.R
import kotlinx.android.synthetic.main.recycler_view_sticky_header_decoration.view.*


class StickyItemDecoration : RecyclerView.ItemDecoration() {

    private var headerView: View ?= null

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        parent?.getChildAdapterPosition(view)?.apply {
            if (isHeaderSection(this)) {
                outRect?.top = 50
                outRect?.bottom = 50
            }
        }
    }

    override fun onDrawOver(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.onDrawOver(canvas, parent, state)

        parent ?: return
        parent.getChildAt(0) ?: return

        headerView = headerView ?: inflateHeaderView(parent).apply {
            fixLayoutSize(parent, this)
        }

        var previousHeader: CharSequence = ""

        for (i in 0 until parent.childCount) {

            val child = parent.getChildAt(i)
            val position = parent.getChildAdapterPosition(child)

            val title = getSectionHeader(position).toString().toUpperCase()
            headerView?.stickyHeader_tvHeader?.text = title

            if (previousHeader.toString().toUpperCase() != title
                    || isHeaderSection(position)) {

                canvas ?: return

                headerView?.let {
                    drawHeader(canvas, child, it)
                }

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
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.paddingLeft.plus(parent.paddingRight).plus(parent.verticalScrollbarWidth),
                view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop.plus(parent.paddingBottom), view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)


        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun isHeaderSection(position: Int): Boolean
            = getItemText?.let {
        position == 0
                || getSectionHeader(position).toString().toLowerCase() != getSectionHeader(position.minus(1)).toString().toLowerCase()
    } ?: false

    var getItemText: ((Int) -> String) ?= null

    private fun getSectionHeader(position: Int)
            = getItemText?.let {
        it(position).subSequence(0, 1)
    } ?: false
}