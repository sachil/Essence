package xyz.sachil.essence.fragment

import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class ArticleFragment : AbstractTypePagerFragment() {
    companion object {
        private const val TAG = "ArticleFragment"
        private val CATEGORY = Utils.Category.ARTICLE
    }

    override fun getCategory(): Utils.Category = CATEGORY
}