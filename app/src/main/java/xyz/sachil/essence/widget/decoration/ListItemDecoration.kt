package xyz.sachil.essence.widget.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListItemDecoration(
    private val decoration: Drawable,
    private val decorationSize: Int,
    private val containFirstItemTop: Boolean = true,
    private val containLastItemBottom: Boolean = true,
) : RecyclerView.ItemDecoration() {

    private val decorationWidth: Int =
        if (decoration.intrinsicWidth == -1) decorationSize else decoration.intrinsicWidth

    private val decorationHeight: Int =
        if (decoration.intrinsicHeight == -1) decorationSize else decoration.intrinsicHeight


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val totalChildCount = parent.adapter!!.itemCount
        val layoutManager = parent.layoutManager
        if (layoutManager != null && layoutManager is LinearLayoutManager) {
            when (layoutManager.orientation) {
                RecyclerView.VERTICAL -> {
                    if (containFirstItemTop && position == 0) {
                        outRect.set(0, decorationHeight, 0, decorationHeight)
                    } else if (!containLastItemBottom && position == totalChildCount - 1) {
                        outRect.set(0, 0, 0, 0)
                    } else {
                        outRect.set(0, 0, 0, decorationHeight)
                    }
                }
                RecyclerView.HORIZONTAL -> {
                    if (containFirstItemTop && position == 0) {
                        outRect.set(decorationWidth, 0, decorationWidth, 0)
                    } else if (!containLastItemBottom && position == totalChildCount - 1) {
                        outRect.set(0, 0, 0, 0)
                    } else {
                        outRect.set(0, 0, decorationWidth, 0)
                    }

                }
            }

        } else {
            super.getItemOffsets(outRect, view, parent, state)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager != null && layoutManager is LinearLayoutManager) {
            when (layoutManager.orientation) {
                //当recyclerview是垂直滑动，则画水平装饰
                RecyclerView.VERTICAL -> {
                    drawHorizontalDecoration(c, parent)
                }
                //当recyclerview是水平滑动，则画垂直装饰
                RecyclerView.HORIZONTAL -> {
                    drawVerticalDecoration(c, parent)
                }
            }
        }
    }

    //画垂直方向上的decoration
    private fun drawVerticalDecoration(canvas: Canvas, parent: RecyclerView) {
        var child: View
        var layoutParams: RecyclerView.LayoutParams
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        var currentItemPosition: Int
        val totalItemCount = parent.adapter!!.itemCount
        //这里的parent.childCount应该是只是当前显示的item的count
        for (index in 0 until parent.childCount) {
            child = parent.getChildAt(index)
            layoutParams = child.layoutParams as RecyclerView.LayoutParams
            currentItemPosition = layoutParams.viewLayoutPosition

            //如果不需要在最后一个item的右侧绘制decoration，则直接跳过它
            if (!containLastItemBottom && currentItemPosition == totalItemCount - 1) {
                continue
            }
            left = child.right + layoutParams.rightMargin
            right = left + decorationWidth
            top = child.top - layoutParams.topMargin
            bottom = child.bottom + layoutParams.bottomMargin
            decoration.setBounds(left, top, right, bottom)
            decoration.draw(canvas)

            //如果需要在第一个item的左侧绘制decoration
            if (containFirstItemTop && currentItemPosition == 0) {
                left = child.left-layoutParams.leftMargin-decorationWidth
                right = left+decorationWidth
                decoration.setBounds(left, top, right, bottom)
                decoration.draw(canvas)
            }
        }
    }

    //画水平方向上的decoration
    private fun drawHorizontalDecoration(canvas: Canvas, parent: RecyclerView) {
        var child: View
        var layoutParams: RecyclerView.LayoutParams
        var left: Int
        var top: Int
        var right: Int
        var bottom: Int
        var currentItemPosition: Int
        val totalItemCount = parent.adapter!!.itemCount
        //这里的parent.childCount应该是只是当前显示的item的count
        for (index in 0 until parent.childCount) {
            child = parent.getChildAt(index)
            layoutParams = child.layoutParams as RecyclerView.LayoutParams
            currentItemPosition = layoutParams.viewLayoutPosition
            //如果不需要在最后一个item的底部绘制decoration，则直接跳过它
            if (!containLastItemBottom && currentItemPosition == totalItemCount - 1) {
                continue
            }
            left = child.left - layoutParams.leftMargin
            top = child.bottom + layoutParams.bottomMargin
            right = child.right + layoutParams.rightMargin
            bottom = top + decorationHeight
            decoration.setBounds(left, top, right, bottom)
            decoration.draw(canvas)

            //如果需要在第一个item的顶部绘制decoration
            if (containFirstItemTop && currentItemPosition == 0) {
                top = child.top - layoutParams.topMargin - decorationHeight
                bottom = top + decorationHeight
                decoration.setBounds(left, top, right, bottom)
                decoration.draw(canvas)
            }
        }
    }

}