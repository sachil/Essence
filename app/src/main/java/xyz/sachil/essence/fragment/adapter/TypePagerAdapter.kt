package xyz.sachil.essence.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.fragment.TypeDataFragment
import xyz.sachil.essence.model.net.bean.Type

class TypePagerAdapter(
    fragment: Fragment,
    private val category: Utils.Category,
    private val typeList: List<String>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = typeList.size

    @KoinApiExtension
    override fun createFragment(position: Int): Fragment {
        return TypeDataFragment.newInstance(category, typeList[position])
    }

    fun getItem(position: Int) = typeList[position]

}