package xyz.sachil.essence.fragment.vh

import androidx.recyclerview.widget.RecyclerView
import xyz.sachil.essence.R
import xyz.sachil.essence.databinding.RecyclerItemImageTextBinding
import xyz.sachil.essence.model.net.GlideApp
import xyz.sachil.essence.model.net.bean.TypeData

class ImageTextViewHolder(private val viewBinding: RecyclerItemImageTextBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {
        const val ITEM_TYPE_IMAGE_TEXT = 0x04
    }

    fun bindView(typeData: TypeData) {
        viewBinding.title.text = typeData.title.replace("\n", "")
        viewBinding.author.text = typeData.author
        //只显示年、月、日
        viewBinding.publishDate.text = typeData.publishedDate.subSequence(0, 10)
        viewBinding.description.text = typeData.description.replace("\n", "")
        //使用自定义配置的glide加载图片
        GlideApp.with(viewBinding.root)
            .load(typeData.images[0])
            .placeholder(R.drawable.icon_recycler_item_loading_normal)
            .error(R.drawable.icon_recycler_item_failed_normal)
            .optionalCenterCrop()
            .into(viewBinding.image)
        viewBinding.viewCounts.text = "${typeData.viewCounts}"
        viewBinding.likeCounts.text = "${typeData.likeCounts}"
    }
}