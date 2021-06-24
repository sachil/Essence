package xyz.sachil.essence.fragment.pager

import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils

@KoinApiExtension
class ArticleFragment : AbstractPagerFragment() {
    companion object {
        private const val TAG = "ArticleFragment"
        private val CATEGORY = Utils.Category.ARTICLE
    }

    override fun getCategory(): Utils.Category = CATEGORY
}