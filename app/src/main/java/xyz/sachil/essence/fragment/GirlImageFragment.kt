package xyz.sachil.essence.fragment

import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class GirlImageFragment : AbstractTypeDataFragment() {
    companion object {
        private const val TAG = "GirlFragment"
        private val CATEGORY = Utils.Category.GIRL
    }

    override fun getCategoryAndType() {
        category = CATEGORY
        type = CATEGORY.type
    }
}