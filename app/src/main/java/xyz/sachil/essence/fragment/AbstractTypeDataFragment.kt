package xyz.sachil.essence.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.R
import xyz.sachil.essence.activity.ImageActivity
import xyz.sachil.essence.activity.MarkdownActivity
import xyz.sachil.essence.util.addListItemDecoration
import xyz.sachil.essence.databinding.FragmentTypeDataBinding
import xyz.sachil.essence.fragment.adapter.OnItemClickedListener
import xyz.sachil.essence.fragment.adapter.TypeDataAdapter
import xyz.sachil.essence.fragment.vh.PlainImageViewHolder
import xyz.sachil.essence.util.getWeeklyPopularType
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.repository.*
import xyz.sachil.essence.util.setWeeklyPopularType
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.vm.SharedViewModel
import xyz.sachil.essence.vm.TypeDataViewModel

@KoinApiExtension
abstract class AbstractTypeDataFragment : Fragment() {

    companion object {
        private const val TAG = "TypeDataFragment"
    }

    protected lateinit var category: Utils.Category
    protected lateinit var type: String
    private val typeDataViewModel by viewModels<TypeDataViewModel>()
    private val sharedViewModel by viewModels<SharedViewModel>({ requireParentFragment() })
    private lateinit var viewBinding: FragmentTypeDataBinding
    private lateinit var typeDataAdapter: TypeDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentTypeDataBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getCategoryAndType()
        initViews()
        addObservers()
        requestTypeData(category, type)
    }

    protected abstract fun getCategoryAndType()

    private fun initViews() {
        initSwipeRefreshLayout()
        initAdapter()
        initRecyclerView()
    }

    private fun addObservers() {
        //观察本周最热popularType的变化
        sharedViewModel.weeklyPopularRequest.observe(viewLifecycleOwner) {
            if (it.popularType != requireContext().getWeeklyPopularType()) {
                Log.e(TAG, "#####:${it.category.type},${it.type},${it.popularType}")
                requireContext().setWeeklyPopularType(it.popularType)
                requestTypeData(it.category, it.type)
            }
        }
        //观察返回的数据
        typeDataViewModel.typeData.observe(viewLifecycleOwner) {
            //用于防止无谓的刷新
            if (this.isVisible) {
                if (it.loadedCount > 0) {
                    Log.e(TAG, "$category,$type,${it[0]?.category}")
                }

                typeDataAdapter.submitList(it) {
                    showEmptyView()
                }
            }
        }
        //观察状态变化
        typeDataViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is Refreshing -> {
                    if (!viewBinding.swipeRefreshLayout.isRefreshing) {
                        viewBinding.swipeRefreshLayout.isRefreshing = true
                    }
                }
                else -> {
                    if (viewBinding.swipeRefreshLayout.isRefreshing) {
                        viewBinding.swipeRefreshLayout.isRefreshing = false
                    }
                }
            }
            typeDataAdapter.updateLoadState(it)
        }
        //观察是否发生异常
        typeDataViewModel.error.observe(viewLifecycleOwner) {

        }
    }

    private fun showEmptyView() {
        if (typeDataAdapter.getRealItemCount() > 0) {
            viewBinding.emptyView.visibility = View.GONE
            viewBinding.recyclerView.visibility = View.VISIBLE
        } else {
            viewBinding.emptyView.visibility = View.VISIBLE
            viewBinding.recyclerView.visibility = View.GONE
        }
    }

    private fun initSwipeRefreshLayout() {
        viewBinding.swipeRefreshLayout.apply {
            setColorSchemeColors(
                resources.getColor(R.color.secondaryColor),
                resources.getColor(R.color.primaryColor)
            )
            setOnRefreshListener {
                typeDataViewModel.refresh()
            }
        }
    }

    private fun initAdapter() {
        typeDataAdapter = TypeDataAdapter()
        typeDataAdapter.itemClickListener = getItemClickListener()
    }

    private fun getItemClickListener(): OnItemClickedListener =
        OnItemClickedListener { _, position ->
            val itemData = typeDataAdapter.currentList?.get(position) as TypeData
            val itemType = typeDataAdapter.getItemViewType(position)
            val intent = Intent()
            intent.putExtra("title",itemData.title)
            if (itemType == PlainImageViewHolder.ITEM_TYPE_IMAGE) {
                intent.setClass(requireContext(), ImageActivity::class.java)
                intent.putExtra("image", itemData.images[0])
            } else {
                intent.setClass(requireContext(), MarkdownActivity::class.java)
                intent.putExtra("id", itemData.id)
            }
            startActivity(intent)
        }

    private fun initRecyclerView() {
        viewBinding.recyclerView.apply {
            adapter = typeDataAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            //添加分割线
            addListItemDecoration()
            //关闭item动画，可以避免闪烁
            itemAnimator = null
        }
    }

    private fun RecyclerView.addListItemDecoration() {
        when (type) {
            Utils.Category.GIRL.type -> {
                setBackgroundColor(resources.getColor(R.color.backgroundColor))
                addListItemDecoration(
                    R.color.backgroundColor, containFirstItemTop = true,
                    containLastItemBottom = true
                )
            }
            else -> {
                setBackgroundColor(resources.getColor(R.color.surfaceColor))
                addListItemDecoration(R.color.backgroundColor)
            }
        }
    }

    private fun requestTypeData(category: Utils.Category, type: String) {
        if (category == Utils.Category.WEEKLY_POPULAR) {
            typeDataViewModel.getWeeklyPopular(
                category,
                type,
                requireContext().getWeeklyPopularType()
            )
        } else {
            typeDataViewModel.getTypeData(category, type)
        }
    }


}