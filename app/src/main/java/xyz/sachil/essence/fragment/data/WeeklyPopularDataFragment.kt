package xyz.sachil.essence.fragment.data

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import org.koin.core.component.KoinApiExtension
import xyz.sachil.essence.model.net.bean.TypeData
import xyz.sachil.essence.util.Utils
import xyz.sachil.essence.util.getWeeklyPopularType
import xyz.sachil.essence.util.setWeeklyPopularType
import xyz.sachil.essence.vm.SharedViewModel
import xyz.sachil.essence.vm.WeeklyPopularViewModel

@KoinApiExtension
class WeeklyPopularDataFragment : AbstractDataFragment() {
    companion object {
        private const val TAG = "WeeklyPopularDataFragment"
        private const val KEY_CATEGORY = "category"
        private const val KEY_TYPE = "type"

        fun newInstance(category: Utils.Category, type: String): WeeklyPopularDataFragment {
            val params = Bundle()
            params.putString(KEY_CATEGORY, category.type)
            params.putString(KEY_TYPE, type)
            val fragment = WeeklyPopularDataFragment()
            fragment.arguments = params
            return fragment
        }
    }

    private val sharedViewModel by viewModels<SharedViewModel>({ requireParentFragment() })
    private val dataViewModel by viewModels<WeeklyPopularViewModel>()
    private lateinit var popularType: Utils.PopularType

    override fun registerObservers() {
        sharedViewModel.weeklyPopularType.observe(viewLifecycleOwner) {
            if (::popularType.isInitialized && popularType != it) {
                this.popularType = it
                requireContext().setWeeklyPopularType(it)
                requestData()
            }
        }

        dataViewModel.typeData.observe(viewLifecycleOwner) {
            notifyDataChanged(it)
        }

        dataViewModel.state.observe(viewLifecycleOwner) {
            updateLoadState(it)
        }

        dataViewModel.error.observe(viewLifecycleOwner) {
            val message = it.getMessageIfNotHandled()
            if (message != null) {
                showErrorMessage(message)
            }
        }
    }

    override fun requestData() {
        popularType = requireContext().getWeeklyPopularType()
        dataViewModel.getWeeklyPopularData(type, popularType.type)
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