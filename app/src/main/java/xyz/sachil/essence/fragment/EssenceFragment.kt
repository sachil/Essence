package xyz.sachil.essence.fragment

import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class EssenceFragment : AbstractTypePagerFragment() {
    companion object {
        private const val TAG = "EssenceFragment"
        private val CATEGORY = Utils.Category.GAN_HUO
    }

    override fun getCategory(): Utils.Category = CATEGORY
}