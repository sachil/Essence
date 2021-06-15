package xyz.sachil.essence.fragment.vh

import androidx.recyclerview.widget.RecyclerView
import xyz.sachil.essence.databinding.RecyclerItemStateFailedBinding

class FailedStateViewHolder(private val viewBinding: RecyclerItemStateFailedBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    companion object {
        const val ITEM_TYPE_STATE_FAILED = 0x01
    }
}