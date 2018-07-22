package com.githubapi.search.searchgithubusers

import android.content.res.Resources
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(private val recyclerViewId: Int) {

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View>
    = object : TypeSafeMatcher<View>() {
        var resources: Resources ?= null
        var childView: View ?= null

        override fun describeTo(description: Description?) {
            var idDescription = recyclerViewId.toString()

            try {
                idDescription = resources!!.getResourceName(recyclerViewId)
            } catch (exception: Resources.NotFoundException) {
                idDescription = "$recyclerViewId (resource name is not found)"
            }

            description?.appendText("RecyclerView with id: $idDescription at position: $position")
        }

        override fun matchesSafely(item: View?): Boolean {
            resources = item?.resources

            childView ?: let {
                val recyclerView = item?.rootView?.findViewById(recyclerViewId) as RecyclerView
                recyclerView?.let { rv ->
                    if (rv.id == recyclerViewId) {
                        val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                        childView = viewHolder.itemView
                    } else {
                        return false
                    }
                } ?: return false
            }

            return when (targetViewId) {
                -1 -> item === childView
                else -> item === childView?.findViewById<View>(targetViewId)
            }
        }
    }

    fun <VIEW_HOLDER: RecyclerView.ViewHolder> withHolderTextView(text: String, textResId: Int, clazz: Class<VIEW_HOLDER>)
    = object : BoundedMatcher<RecyclerView.ViewHolder, VIEW_HOLDER>(clazz) {

        override fun describeTo(description: Description?) {
            description?.appendText("No ViewHolder found with text: $text")
        }

        override fun matchesSafely(item: VIEW_HOLDER): Boolean {
            val textView = item.itemView.findViewById<TextView>(textResId)

            textView ?: return false
            return textView.text.toString().contains(text)
        }

    }
}