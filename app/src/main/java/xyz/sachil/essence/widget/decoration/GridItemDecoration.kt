package xyz.sachil.essence.widget.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class GridItemDecoration(
    private val horizontalDecoration: Drawable,
    horizontalDecorationSize: Int,
    private val verticalDecoration: Drawable,
    verticalDecorationSize: Int,
) : RecyclerView.ItemDecoration() {

    companion object {
        private const val TAG = "GridItemDecoration"
    }

    //decoration的宽度，也就是垂直方向decoration的宽度
    private val decorationWidth: Int = if (verticalDecoration.intrinsicWidth == -1)
        verticalDecorationSize
    else
        verticalDecoration.intrinsicWidth

    //decoration的高度，也就是水平方向decoration的高度
    private val decorationHeight: Int = if (horizontalDecoration.intrinsicHeight == -1)
        horizontalDecorationSize
    else
        horizontalDecoration.intrinsicHeight

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val spanCount = getSpanCount(parent)
        if (spanCount == 0) {
            super.getItemOffsets(outRect, view, parent, state)
        } else {
            //不论recyclerView滑动的方向是横向还是纵向，这里的行和列都是指的是在正常情况下的行和列
            val rowIndex = getRowIndex(parent, view)
            val columnIndex = getColumnIndex(parent, view)
            outRect.left = if (columnIndex == 0) decorationWidth else 0
            outRect.top = if (rowIndex == 0) decorationHeight else 0
            outRect.right = decorationWidth
            outRect.bottom = decorationHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //画水平方向的decoration
        drawHorizontalDecoration(c, parent)
        //画垂直方向的decoration
        drawVerticalDecoration(c, parent)
    }

    protected open fun isLayoutManagerValid(parent: RecyclerView): Boolean = parent.layoutManager !=
            null && parent.layoutManager is GridLayoutManager

    protected open fun getSpanCount(parent: RecyclerView): Int {
        return if (isLayoutManagerValid(parent)) {
            (parent.layoutManager as GridLayoutManager).spanCount
        } else 0
    }

    protected open fun getOrientation(parent: RecyclerView): Int {
        return if (isLayoutManagerValid(parent)) {
            (parent.layoutManager as GridLayoutManager).orientation
        } else -1
    }

    protected open fun getRowIndex(parent: RecyclerView, view: View): Int {
        val orientation = getOrientation(parent)
        val spanCount = getSpanCount(parent)
        val position = parent.getChildAdapterPosition(view)
        return if (orientation == RecyclerView.VERTICAL) {
            position / spanCount
        } else {
            position % spanCount
        }
    }

    protected open fun getColumnIndex(parent: RecyclerView, view: View): Int {
        val orientation = getOrientation(parent)
        val spanCount = getSpanCount(parent)
        val position = parent.getChildAdapterPosition(view)
        return if (orientation == RecyclerView.VERTICAL) {
            position % spanCount
        } else {
            position / spanCount
        }
    }


    /**
     * 画垂直方向上的decoration，包括左边的decoration，和右边的decoration
     */
    private fun drawVerticalDecoration(canvas: Canvas, parent: RecyclerView) {
        val spanCount = getSpanCount(parent)
        if (spanCount == 0)
            return
        val orientation = getOrientation(parent)
        if (orientation == -1)
            return
        var child: View
        var layoutParams: RecyclerView.LayoutParams
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        var currentItemPosition: Int
        for (index in 0 until parent.childCount) {
            child = parent.getChildAt(index)
            layoutParams = child.layoutParams as RecyclerView.LayoutParams
            currentItemPosition = layoutParams.viewLayoutPosition
            //处理水平方向和垂直方向的decoration交汇时的十字区域的细节，
            // 垂直滑动时，十字区域显示垂直方向的decoration,水平滑动时，十字区域显示水平方向的decoration
            val additional = if (orientation == RecyclerView.HORIZONTAL) 0 else decorationHeight
            //先计算出decoration的上下边界，在画垂直方向的decoration时，它们是不会改变的
            top = child.top - layoutParams.topMargin - additional
            bottom = child.bottom + layoutParams.bottomMargin + additional
            //再计算item右边decoration的边界
            left = child.right + layoutParams.rightMargin
            right = left + decorationWidth
            //画出item右边的decoration
            verticalDecoration.setBounds(left, top, right, bottom)
            verticalDecoration.draw(canvas)

            //当item位于第一列时，需要画item左边的decoration
            if (getColumnIndex(parent, child) == 0) {
                //计算item左边decoration的边界
                right = child.left - layoutParams.leftMargin
                left = right - decorationWidth
                //画出item左边的decoration
                verticalDecoration.setBounds(left, top, right, bottom)
                verticalDecoration.draw(canvas)
            }
        }
    }

    /**
     * 画水平方向上的decoration，包括顶部的decoration和底部的decoration
     */
    private fun drawHorizontalDecoration(canvas: Canvas, parent: RecyclerView) {
        val spanCount = getSpanCount(parent)
        if (spanCount == 0)
            return
        val orientation = getOrientation(parent)
        if (orientation == -1)
            return

        var child: View
        var layoutParams: RecyclerView.LayoutParams
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        for (index in 0 until parent.childCount) {
            child = parent.getChildAt(index)
            layoutParams = child.layoutParams as RecyclerView.LayoutParams
            //处理水平方向和垂直方向的decoration交汇时的十字区域的细节，
            // 垂直滑动时，十字区域显示垂直方向的decoration,水平滑动时，十字区域显示水平方向的decoration
            val additional = if (orientation == RecyclerView.VERTICAL) 0 else decorationWidth
            //首先计算decoration的左右边界，因为它们在画水平方向的decoration时不会改变
            left = child.left - layoutParams.leftMargin - additional
            right = child.right + layoutParams.rightMargin + additional
            //再计算底部decoration的边界
            top = child.bottom + layoutParams.bottomMargin
            bottom = top + decorationHeight
            //画出item底部的decoration
            horizontalDecoration.setBounds(left, top, right, bottom)
            horizontalDecoration.draw(canvas)

            //当item处在第一行时，需要画item顶部的decoration
            if (getRowIndex(parent, child) == 0) {
                Log.e("***","11111")
                //先计算item顶部decoration的边界
                bottom = child.top - layoutParams.topMargin
                top = bottom - decorationHeight
                //画出item顶部的decoration
                horizontalDecoration.setBounds(left, top, right, bottom)
                horizontalDecoration.draw(canvas)
            }
        }
    }

}