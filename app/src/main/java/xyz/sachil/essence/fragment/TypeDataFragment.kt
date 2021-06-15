package xyz.sachil.essence.fragment

import android.os.Bundle
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils

@KoinApiExtension
open class TypeDataFragment : AbstractTypeDataFragment() {
    companion object {
        private const val TAG = "DataListFragment"
        private const val KEY_CATEGORY = "category"
        private const val KEY_TYPE = "type"

        fun newInstance(category: Utils.Category, type: String): TypeDataFragment {
            val params = Bundle()
            params.putString(KEY_CATEGORY, category.type)
            params.putString(KEY_TYPE, type)
            val fragment = TypeDataFragment()
            fragment.arguments = params
            return fragment
        }
    }

    override fun getCategoryAndType() {
        var argument = requireArguments().getString(KEY_CATEGORY)
        if (argument != null) {
            category = when (argument) {
                Utils.Category.ARTICLE.type -> {
                    Utils.Category.ARTICLE
                }
                Utils.Category.GAN_HUO.type -> {
                    Utils.Category.GAN_HUO
                }
                Utils.Category.WEEKLY_POPULAR.type -> {
                    Utils.Category.WEEKLY_POPULAR
                }
                else -> {
                    Utils.Category.GIRL
                }
            }
        }
        argument = requireArguments().getString(KEY_TYPE)
        if (argument != null) {
            type = argument
        }
    }
}