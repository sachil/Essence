package xyz.sachil.essence.fragment.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import xyz.sachil.essence.model.net.bean.TypeData

class TypeDataDiffUtil : DiffUtil.ItemCallback<TypeData>() {

    override fun areItemsTheSame(oldItem: TypeData, newItem: TypeData): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TypeData, newItem: TypeData): Boolean =
        (oldItem.title == newItem.title
                && oldItem.author == newItem.author
                && oldItem.viewCounts == newItem.viewCounts
                && oldItem.likeCounts == newItem.likeCounts)
}