package xyz.sachil.essence.fragment.data

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.R
import xyz.sachil.essence.activity.ImageActivity
import xyz.sachil.essence.activity.MarkdownActivity
import xyz.sachil.essence.databinding.FragmentTypeDataBinding
import xyz.sachil.essence.fragment.adapter.OnItemClickedListener
import xyz.sachil.essence.fragment.adapter.TypeDataAdapter
import xyz.sachil.essence.fragment.vh.PlainImageViewHolder
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.util.*

@KoinApiExtension
abstract class AbstractDataFragment : Fragment() {

    companion object {
        private const val TAG = "TAbstractDataFragment"
        const val KEY_CATEGORY = "category"
        const val KEY_TYPE = "type"
    }

    protected lateinit var category: String
    protected lateinit var type: String


    protected lateinit var viewBinding: FragmentTypeDataBinding
    protected lateinit var typeDataAdapter: TypeDataAdapter

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
        registerObservers()
        requestData()
    }

    protected abstract fun registerObservers()
    protected abstract fun requestData()
    protected abstract fun refreshData()

    protected open fun getCategoryAndType() {
        var argument = requireArguments().getString(KEY_CATEGORY)
        if (argument != null) {
            category = argument
        }
        argument = requireArguments().getString(KEY_TYPE)
        if (argument != null) {
            type = argument
        }
    }

    //用于显示或者隐藏emptyView
    protected fun updateView() {
        if (typeDataAdapter.getRealItemCount() > 0) {
            viewBinding.emptyView.visibility = View.GONE
            viewBinding.recyclerView.visibility = View.VISIBLE
        } else {
            viewBinding.emptyView.visibility = View.VISIBLE
            viewBinding.recyclerView.visibility = View.GONE
        }
    }

    protected fun updateLoadState(loadState: LoadState) {
        when (loadState) {
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
        typeDataAdapter.updateLoadState(loadState)
    }

    protected fun showErrorMessage(@StringRes message: Int) {
        viewBinding.snackBarContainer.showErrorMessage(message)
    }

    private fun initViews() {
        initSwipeRefreshLayout()
        initAdapter()
        initRecyclerView()
    }

    private fun initSwipeRefreshLayout() {
        viewBinding.swipeRefreshLayout.apply {
            setColorSchemeColors(
                resources.getColor(R.color.secondaryColor),
                resources.getColor(R.color.primaryColor)
            )
            setOnRefreshListener {
                refreshData()
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
            intent.putExtra("title", itemData.title)
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

}