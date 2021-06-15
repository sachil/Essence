package xyz.sachil.essence.fragment.vh

import androidx.recyclerview.widget.RecyclerView
import xyz.sachil.essence.databinding.RecyclerItemStateLoadingBinding

class LoadingStateViewHolder(private val viewBinding: RecyclerItemStateLoadingBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {
    companion object {
        const val ITEM_TYPE_STATE_LOADING = 0x00
    }
}