package xyz.sachil.essence.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.fragment.data.DataFragment
import xyz.sachil.essence.fragment.data.WeeklyPopularDataFragment

@KoinApiExtension
class TypePagerAdapter(
    fragment: Fragment,
    private val category: Utils.Category,
    private val typeList: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = typeList.size

    override fun createFragment(position: Int): Fragment {
        return if (category == Utils.Category.WEEKLY_POPULAR) {
            WeeklyPopularDataFragment.newInstance(category, typeList[position])
        } else {
            DataFragment.newInstance(category, typeList[position])
        }
    }

    fun getItem(position: Int) = typeList[position]
}