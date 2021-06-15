package xyz.sachil.essence.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.sachil.essence.util.Utils

class SharedViewModel : ViewModel() {

    private val internalWeeklyPopularRequest = MutableLiveData<WeeklyPopularRequest>()
    val weeklyPopularRequest: LiveData<WeeklyPopularRequest> get() = internalWeeklyPopularRequest

    fun setWeeklyPopularRequest(
        category: Utils.Category,
        type: String,
        popularType: Utils.PopularType
    ) {
        internalWeeklyPopularRequest.value = WeeklyPopularRequest(category, type, popularType)
    }


    data class WeeklyPopularRequest(
        val category: Utils.Category,
        val type: String,
        val popularType: Utils.PopularType
    )

}