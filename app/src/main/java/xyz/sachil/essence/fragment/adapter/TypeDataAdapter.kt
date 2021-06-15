package xyz.sachil.essence.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.databinding.RecyclerItemImageTextBinding
import xyz.sachil.essence.databinding.RecyclerItemPlainImageBinding
import xyz.sachil.essence.databinding.RecyclerItemPlainTextBinding
import xyz.sachil.essence.fragment.adapter.diff.TypeDataDiffUtil
import xyz.sachil.essence.fragment.vh.ImageTextViewHolder
import xyz.sachil.essence.fragment.vh.ImageTextViewHolder.Companion.ITEM_TYPE_IMAGE_TEXT
import xyz.sachil.essence.fragment.vh.PlainImageViewHolder
import xyz.sachil.essence.fragment.vh.PlainImageViewHolder.Companion.ITEM_TYPE_IMAGE
import xyz.sachil.essence.fragment.vh.PlainTextDataViewHolder
import xyz.sachil.essence.fragment.vh.PlainTextDataViewHolder.Companion.ITEM_TYPE_PLAIN_TEXT
import xyz.sachil.essence.util.hasImage
import xyz.sachil.essence.model.net.bean.TypeData

class TypeDataAdapter : AbstractPagedListAdapter<TypeData>(diffUtil) {

    companion object {
        private const val TAG = "TypeDataAdapter"
        val diffUtil = TypeDataDiffUtil()
    }

    var itemClickListener: OnItemClickedListener? = null


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlainTextDataViewHolder -> {
                holder.bindView(getItem(position)!!)
            }
            is ImageTextViewHolder -> {
                holder.bindView(getItem(position)!!)
            }
            is PlainImageViewHolder -> {
                holder.bindView(getItem(position)!!, position)
            }
            else -> {
                super.onBindViewHolder(holder, position)
            }
        }

        if (itemClickListener != null) {
            holder.itemView.setOnClickListener {
                itemClickListener?.onItemClicked(it, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_TYPE_PLAIN_TEXT -> {
                PlainTextDataViewHolder(
                    RecyclerItemPlainTextBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }

            ITEM_TYPE_IMAGE_TEXT -> {
                ImageTextViewHolder(
                    RecyclerItemImageTextBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM_TYPE_IMAGE -> {
                val viewBinding = RecyclerItemPlainImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PlainImageViewHolder(viewBinding)
            }

            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var itemViewType = super.getItemViewType(position)
        if (itemViewType == ITEM_TYPE_STATE_UNKNOWN) {
            val item = getItem(position)
            if (item != null) {
                itemViewType = if (item.type == Utils.Category.GIRL.type) {
                    ITEM_TYPE_IMAGE
                } else {
                    if (item.hasImage()) ITEM_TYPE_IMAGE_TEXT else ITEM_TYPE_PLAIN_TEXT
                }
            }
        }
        return itemViewType
    }
}