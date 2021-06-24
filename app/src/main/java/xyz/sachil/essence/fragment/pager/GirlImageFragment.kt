package xyz.sachil.essence.fragment.pager

import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.fragment.data.AbstractDataFragment
import xyz.sachil.essence.fragment.data.DataFragment
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class GirlImageFragment : DataFragment() {
    companion object {
        private const val TAG = "GirlFragment"
        private val CATEGORY = Utils.Category.GIRL.type
    }

    override fun getCategoryAndType() {
        category = CATEGORY
        type = CATEGORY
    }


}