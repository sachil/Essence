package xyz.sachil.essence.fragment.adapter

import android.view.View

fun interface OnItemClickedListener {
    fun onItemClicked(view: View, position: Int)
}