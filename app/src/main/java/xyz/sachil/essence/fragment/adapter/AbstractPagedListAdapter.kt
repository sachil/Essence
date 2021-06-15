package xyz.sachil.essence.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import xyz.sachil.essence.databinding.RecyclerItemStateFailedBinding
import xyz.sachil.essence.databinding.RecyclerItemStateLoadingBinding
import xyz.sachil.essence.databinding.RecyclerItemStateNoMoreBinding
import xyz.sachil.essence.fragment.vh.FailedStateViewHolder
import xyz.sachil.essence.fragment.vh.FailedStateViewHolder.Companion.ITEM_TYPE_STATE_FAILED
import xyz.sachil.essence.fragment.vh.LoadingStateViewHolder
import xyz.sachil.essence.fragment.vh.LoadingStateViewHolder.Companion.ITEM_TYPE_STATE_LOADING
import xyz.sachil.essence.fragment.vh.NoMoreStateViewHolder
import xyz.sachil.essence.fragment.vh.NoMoreStateViewHolder.Companion.ITEM_TYPE_STATE_NO_MORE
import xyz.sachil.essence.repository.*

abstract class AbstractPagedListAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    PagedListAdapter<T, RecyclerView.ViewHolder>(diffUtil) {
    var onItemClickedListener: OnItemClickedListener? = null
    private var loadState: LoadState? = null

    //判断是否需要显示加载状态
    private val isLoadStateShowing: Boolean
        get() = loadState != null
                && loadState !is Success
                && loadState !is Refreshing

    companion object {
        const val ITEM_TYPE_STATE_UNKNOWN = -0x01
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LoadingStateViewHolder -> {

            }
            is FailedStateViewHolder -> {

            }
            is NoMoreStateViewHolder -> {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_STATE_LOADING -> {
                val viewBinding = RecyclerItemStateLoadingBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
                LoadingStateViewHolder(viewBinding)
            }
            ITEM_TYPE_STATE_FAILED -> {
                val viewBinding = RecyclerItemStateFailedBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
                FailedStateViewHolder(viewBinding)
            }
            ITEM_TYPE_STATE_NO_MORE -> {
                val viewBinding = RecyclerItemStateNoMoreBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
                NoMoreStateViewHolder(viewBinding)
            }
            else -> throw Exception()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1 && isLoadStateShowing) {
            when (loadState) {
                is Loading -> ITEM_TYPE_STATE_LOADING
                is Failed -> ITEM_TYPE_STATE_FAILED
                else -> ITEM_TYPE_STATE_NO_MORE
            }
        } else {
            ITEM_TYPE_STATE_UNKNOWN
        }
    }

    override fun getItemCount(): Int = super.getItemCount() + if (isLoadStateShowing) 1 else 0

    /**
     * 用于修复当使用StaggeredGridLayoutManager时，上滑加载更多的状态item空间只占用 1/spanCount的问题
     */
    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val layoutParams = holder.itemView.layoutParams
        if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            val holderPosition = holder.layoutPosition
            val itemType = getItemViewType(holderPosition)
            layoutParams.isFullSpan =
                itemType in (ITEM_TYPE_STATE_UNKNOWN + 1)..ITEM_TYPE_STATE_NO_MORE
        }
    }

    /**
     * 用于修复当使用GridLayoutManager时，上滑加载更多的状态item空间只占用 1/spanCount的问题
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager != null && layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val itemType = getItemViewType(position)
                    return if (itemType in (ITEM_TYPE_STATE_UNKNOWN + 1)..ITEM_TYPE_STATE_NO_MORE)
                        layoutManager.spanCount else 1
                }
            }
        }
    }

    fun getRealItemCount(): Int = super.getItemCount()

    fun updateLoadState(state: LoadState) {
        val lastLoadStateShowing = isLoadStateShowing
        this.loadState = state
        val newLoadStateShowing = isLoadStateShowing
        if (lastLoadStateShowing != newLoadStateShowing) {
            //状态显示从无到有
            if (!lastLoadStateShowing && newLoadStateShowing) {
                notifyItemInserted(super.getItemCount())
            } else {
                //状态显示从有到无
                notifyItemRemoved(super.getItemCount())
            }
        } else {
            notifyItemChanged(super.getItemCount())
        }
    }

}