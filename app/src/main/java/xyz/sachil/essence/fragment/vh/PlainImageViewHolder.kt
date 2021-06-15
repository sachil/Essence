package xyz.sachil.essence.fragment.vh

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import xyz.sachil.essence.R
import xyz.sachil.essence.databinding.RecyclerItemPlainImageBinding
import xyz.sachil.essence.util.hasImage
import xyz.sachil.essence.model.net.GlideApp
import xyz.sachil.essence.model.net.bean.TypeData
import kotlin.math.roundToInt

class PlainImageViewHolder(private val viewBinding: RecyclerItemPlainImageBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    companion object {
        const val ITEM_TYPE_IMAGE = 0x05
    }

    fun bindView(data: TypeData, position: Int) {
        viewBinding.title.text = data.title
        viewBinding.author.text = data.author
        viewBinding.description.text = data.description
        viewBinding.viewCounts.text = "${data.viewCounts}"
        viewBinding.likeCounts.text = "${data.likeCounts}"

        if (data.hasImage()) {
//            GlideApp.with(viewBinding.root)
//                .asBitmap()
//                .load(data.images[0])
//                .into(AdjustImageViewTarget(viewBinding.imageView))
            GlideApp.with(viewBinding.root)
                .load(data.images[0])
                .placeholder(R.drawable.icon_recycler_item_loading_normal)
                .error(R.drawable.icon_recycler_item_failed_normal)
                .optionalCenterCrop()
                .into(viewBinding.image)
        }
    }

    private class AdjustImageViewTarget(private val imageView: ImageView) : CustomTarget<Bitmap>() {
        override fun onLoadCleared(placeholder: Drawable?) {

        }

        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {


            val imageViewWidth = imageView.width - imageView.paddingLeft - imageView.paddingRight
            val scale = resource.width.toFloat() / imageViewWidth
            val imageViewHeight =
                (resource.height * scale).roundToInt() + imageView.paddingTop + imageView.paddingBottom

            val layoutParams = imageView.layoutParams
            layoutParams.height = imageViewHeight
            Log.e("----------", "${imageView.width},${resource.width}x${resource.height}")
            if (imageView.scaleType != ImageView.ScaleType.FIT_XY) {
                imageView.scaleType = ImageView.ScaleType.FIT_XY;
            }
            imageView.setImageBitmap(resource)
        }
    }

}