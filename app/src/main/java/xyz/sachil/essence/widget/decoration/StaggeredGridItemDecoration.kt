package xyz.sachil.essence.widget.decoration

import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridItemDecoration(
    horizontalDecoration: Drawable,
    horizontalDecorationSize: Int,
    verticalDecoration: Drawable,
    verticalDecorationSize: Int,
) : GridItemDecoration(
    horizontalDecoration,
    horizontalDecorationSize,
    verticalDecoration,
    verticalDecorationSize
) {

    override fun isLayoutManagerValid(parent: RecyclerView): Boolean = parent.layoutManager !=
            null && parent.layoutManager is StaggeredGridLayoutManager

    override fun getSpanCount(parent: RecyclerView): Int {
        return if (isLayoutManagerValid(parent)) {
            (parent.layoutManager as StaggeredGridLayoutManager).spanCount
        } else 0
    }

    override fun getOrientation(parent: RecyclerView): Int {
        return if (isLayoutManagerValid(parent)) {
            (parent.layoutManager as StaggeredGridLayoutManager).orientation
        } else -1
    }

    override fun getColumnIndex(parent: RecyclerView, view: View): Int {
        val orientation = getOrientation(parent)
        return if (orientation == RecyclerView.VERTICAL) {
            getSpanIndex(parent, view)
        } else {
            val position = parent.getChildAdapterPosition(view)
            val spanCount = getSpanCount(parent)
            position / spanCount
        }
    }

    override fun getRowIndex(parent: RecyclerView, view: View): Int {
        val orientation = getOrientation(parent)
        return if (orientation == RecyclerView.VERTICAL) {
            val position = parent.getChildAdapterPosition(view)
            val spanCount = getSpanCount(parent)
            position / spanCount
        } else {
            getSpanIndex(parent, view)
        }
    }

    //StaggeredGridLayoutManager中的item不是按照下标来排列的，而是按照上方(或者左边)item之间的高度差来排列的
    private fun getSpanIndex(parent: RecyclerView, view: View): Int {
        val layoutManager = view.layoutParams as StaggeredGridLayoutManager.LayoutParams
        return layoutManager.spanIndex
    }

}