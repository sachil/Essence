package xyz.sachil.essence.fragment.data

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.vm.DataViewModel

@KoinApiExtension
open class DataFragment : AbstractDataFragment() {
    companion object {
        private const val TAG = "DataFragment"

        fun newInstance(category: Utils.Category, type: String): DataFragment {
            val params = Bundle()
            params.putString(KEY_CATEGORY, category.type)
            params.putString(KEY_TYPE, type)
            val fragment = DataFragment()
            fragment.arguments = params
            return fragment
        }
    }

    private val dataViewModel by viewModels<DataViewModel>()

    override fun registerObservers() {
        //观察返回的数据
        dataViewModel.typeData.observe(viewLifecycleOwner) {
            notifyDataChanged(it)
        }
        //观察状态变化
        dataViewModel.state.observe(viewLifecycleOwner) {
            updateLoadState(it)
        }
        //观察是否发生异常
        dataViewModel.error.observe(viewLifecycleOwner) {
            val message = it.getMessageIfNotHandled()
            if (message != null) {
                showErrorMessage(message)
            }
        }
    }

    override fun requestData() {
        dataViewModel.getTypeData(category, type)
    }

    override fun refreshData() {
        dataViewModel.refresh()
    }

    private fun notifyDataChanged(data: PagedList<TypeData>) {
        //用于防止无谓的刷新
        if (this.isVisible) {
            typeDataAdapter.submitList(data) {
                updateView()
            }
        }
    }

}