package xyz.sachil.essence.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.R
import xyz.sachil.essence.util.getWeeklyPopularType
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.vm.SharedViewModel

@KoinApiExtension
class WeeklyPopularFragment : AbstractTypePagerFragment() {
    companion object {
        private const val TAG = "WeeklyHottestFragment"
        private val CATEGORY = Utils.Category.WEEKLY_POPULAR
        private val TYPES = listOf(
            Utils.Category.GAN_HUO.type,
            Utils.Category.ARTICLE.type,
            Utils.Category.GIRL.type
        )
    }

    private val sharedViewModel by viewModels<SharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_weekly_popular, menu)
        val itemId = when (requireContext().getWeeklyPopularType()) {
            Utils.PopularType.VIEWS -> R.id.menu_weekly_popular_sorted_by_view_count
            else -> R.id.menu_weekly_popular_sorted_by_star_count
        }
        menu.findItem(itemId).isChecked = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.isChecked = true
        return when (item.itemId) {
            R.id.menu_weekly_popular_sorted_by_view_count -> {
                sharedViewModel.setWeeklyPopularRequest(CATEGORY, type, Utils.PopularType.VIEWS)
                true
            }
            R.id.menu_weekly_popular_sorted_by_star_count -> {
                sharedViewModel.setWeeklyPopularRequest(CATEGORY, type, Utils.PopularType.LIKES)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun requestPagerData() {
        //需要重写，但是是空实现
    }

    override fun handlePagerData() {
        initAdapter(TYPES)
    }

    override fun setTabMode() {
        viewBinding.tabLayout.tabMode = TabLayout.MODE_FIXED
        viewBinding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
    }

    override fun getCategory(): Utils.Category = CATEGORY
}