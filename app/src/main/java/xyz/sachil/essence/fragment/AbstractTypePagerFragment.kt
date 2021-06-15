package xyz.sachil.essence.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.R
import xyz.sachil.essence.databinding.FragmentPagerBinding
import xyz.sachil.essence.fragment.adapter.TypePagerAdapter
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.vm.TypeViewModel

@KoinApiExtension
abstract class AbstractTypePagerFragment : Fragment() {
    companion object {
        private const val TAG = "TypePagerFragment"
    }

    protected var type: String = Utils.Category.GAN_HUO.type
    protected lateinit var viewBinding: FragmentPagerBinding
    private lateinit var typePagerAdapter: TypePagerAdapter
    private val typeViewModel by viewModels<TypeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentPagerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addObservers()
        if (getCategory() == Utils.Category.WEEKLY_POPULAR) {
            registerPageChangeCallback()
        }
        requestPagerData()
    }

    abstract fun getCategory(): Utils.Category

    protected open fun requestPagerData() {
        typeViewModel.getTypes(getCategory())
    }

    protected fun initAdapter(list: List<String>) {
        typePagerAdapter = TypePagerAdapter(this, getCategory(), list)
        viewBinding.viewPager.adapter = typePagerAdapter
        initTabs(list)
    }

    private fun addObservers() {
        handlePagerData()
        handleError()
    }

    protected open fun handlePagerData() {
        typeViewModel.types.observe(requireActivity()) {
            initAdapter(it.map { type -> type.type })
        }
    }

    protected open fun setTabMode() {
        viewBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        viewBinding.tabLayout.tabGravity = TabLayout.GRAVITY_START
    }

    private fun handleError() {
        typeViewModel.error.observe(requireActivity()) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTabs(list: List<String>) {
        setTabMode()
        bindTabLayoutToViewPager(list)
    }

    //利用TabLayoutMediator将tabLayout和viewPager2进行绑定
    private fun bindTabLayoutToViewPager(list: List<String>) {
        TabLayoutMediator(viewBinding.tabLayout, viewBinding.viewPager) { tab, position ->
            when (list[position]) {
                Utils.Category.GAN_HUO.type -> {
                    tab.setText(R.string.tab_category_type_essence)
                }

                Utils.Category.ARTICLE.type -> {
                    tab.setText(R.string.tab_category_type_article)
                }
                Utils.Category.GIRL.type -> {
                    tab.setText(R.string.tab_category_type_girl)
                }
                else -> {
                    tab.text = list[position]
                }
            }
        }.attach()
    }


    private fun registerPageChangeCallback() {
        viewBinding.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val pagerAdapter = viewBinding.viewPager.adapter
                if (pagerAdapter != null && pagerAdapter is TypePagerAdapter) {
                    type = pagerAdapter.getItem(position)
                }
            }
        })
    }

}