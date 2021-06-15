package xyz.sachil.essence.fragment.vh

import androidx.recyclerview.widget.RecyclerView
import xyz.sachil.essence.databinding.RecyclerItemStateNoMoreBinding

class NoMoreStateViewHolder(private val viewBinding: RecyclerItemStateNoMoreBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    companion object {
        const val ITEM_TYPE_STATE_NO_MORE = 0x02
    }
}