package xyz.sachil.essence.fragment.vh

import androidx.recyclerview.widget.RecyclerView
import xyz.sachil.essence.databinding.RecyclerItemPlainTextBinding
import xyz.sachil.essence.model.net.bean.TypeData

class PlainTextDataViewHolder(private val viewBinding: RecyclerItemPlainTextBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {
        const val ITEM_TYPE_PLAIN_TEXT = 0x03
    }

    fun bindView(typeData: TypeData) {
        viewBinding.title.text = typeData.title.replace("\n", "")
        viewBinding.author.text = typeData.author
        viewBinding.publishDate.text = typeData.publishedDate.subSequence(0, 10)
        viewBinding.description.text = typeData.description.replace("\n", "")
        viewBinding.viewCounts.text = "${typeData.viewCounts}"
        viewBinding.likeCounts.text = "${typeData.likeCounts}"
    }
}