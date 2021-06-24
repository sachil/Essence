package xyz.sachil.essence.fragment.pager

import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class EssenceFragment : AbstractPagerFragment() {
    companion object {
        private const val TAG = "EssenceFragment"
        private val CATEGORY = Utils.Category.GAN_HUO
    }

    override fun getCategory(): Utils.Category = CATEGORY
}